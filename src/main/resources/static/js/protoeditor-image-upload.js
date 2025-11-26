/**
 * 파일 탐색기를 열어 사용자가 이미지 파일을 선택하면 업로드를 실행하는 함수
 * @param {string} imgUrl 업로드 기본 URL (ex: '/api/image')
 * @param {string} csrfHeaderName CSRF 헤더 이름 (ex: 'X-CSRF-TOKEN')
 * @param {string} csrfToken CSRF 토큰
 * @param {string} id 업로드 식별자 (에디터 placeholder 등)
 */
function openFilePickerAndUpload(imgUrl, csrfHeaderName, csrfToken) {
  // 1️⃣ 숨겨진 파일 입력 요소를 생성
  const input = document.createElement("input");
  input.type = "file";
  input.accept = "image/*"; // 이미지 파일만 선택 가능

  // 2️⃣ 파일 선택 시 이벤트 핸들러
  input.onchange = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    try {
      // Blob은 File 자체로도 충분함
      await uploadImageWithStatusMessage(file, imgUrl, csrfHeaderName, csrfToken, '1');
    } catch (error) {
      console.error("파일 업로드 중 오류:", error);
    }
  };

  // 3️⃣ 파일 탐색기 열기
  input.click();
}

/**
 * blob을 업로드하고, 업로드 상태를 window.postMessage로 전송하는 함수
 *
 * @param blob 업로드할 이미지 Blob
 * @param imgUrl 업로드 엔드포인트 기본 URL (ex: "/api/image")
 * @param csrfHeaderName 스프링의 CSRF 헤더 이름 (ex: "X-CSRF-TOKEN")
 * @param csrfToken 스프링의 CSRF 토큰
 * @param id 업로드를 식별하기 위한 ID (placeholderId 등)
 */
async function uploadImageWithStatusMessage(blob, imgUrl, csrfHeaderName, csrfToken, placeholderId) {
  // 업로드 시작 알림
  window.postMessage({ status: "uploading", placeholderId }, window.location.origin);

  try {
    const formData = new FormData();
    formData.append("image", blob);

    const response = await fetch(`${imgUrl}/upload`, {
      method: "POST",
      headers: new Headers({ [csrfHeaderName]: csrfToken }),
      body: formData,
    });

    if (!response.ok) {
      throw new Error(`이미지 업로드 실패 (status ${response.status})`);
    }

    // 서버가 문자열(URL) 또는 JSON 형태로 응답할 수 있으므로 둘 다 처리
    let uploadedUrl;
    const contentType = response.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
      const data = await response.json();
      uploadedUrl = data.url || data || "";
    } else {
      uploadedUrl = (await response.text()).trim();
    }

    if (!uploadedUrl) {
      throw new Error("응답에 업로드된 이미지 URL이 없습니다.");
    }

    // 업로드 완료 알림
    window.postMessage(
      { status: "done", uploadedUrl, placeholderId },
      window.location.origin
    );
  } catch (error) {
    console.error("이미지 업로드 실패:", error);

    // 업로드 실패 알림
    window.postMessage(
      { status: "error", placeholderId, message: error.message },
      window.location.origin
    );
  }
}
