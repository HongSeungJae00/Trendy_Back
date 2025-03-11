document.getElementById("productForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch("http://localhost:8080/api/products", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            alert("상품 등록 성공: " + JSON.stringify(result));
        } else {
            const error = await response.json();
            alert("상품 등록 실패: " + JSON.stringify(error));
        }
    } catch (error) {
        console.error("에러 발생:", error);
        alert("네트워크 에러가 발생했습니다.");
    }
});