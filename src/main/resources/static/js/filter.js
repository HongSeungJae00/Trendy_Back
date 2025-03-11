document.getElementById("filter-submit").addEventListener("click", async () => {
    const filterData = {};

    // 선택된 체크박스 값 가져오기
    document.querySelectorAll("input[type='checkbox']:checked").forEach(input => {
        if (!filterData[input.name]) {
            filterData[input.name] = [];
        }
        filterData[input.name].push(input.value.trim());
    });

    if (Object.keys(filterData).length === 0) {
        alert("적어도 하나의 필터를 선택하세요!");
        return;
    }

    try {
        const response = await fetch("/api/products/filter-data", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(filterData)
        });

        if (response.ok) {
            const results = await response.json();
            displayResults(results);
        } else {
            alert("필터링에 실패했습니다.");
        }
    } catch (error) {
        console.error("에러 발생:", error);
        alert("네트워크 에러가 발생했습니다.");
    }
});

function displayResults(results) {
    const resultContainer = document.getElementById("filter-results");
    if (results.length === 0) {
        resultContainer.innerHTML = "<p>필터 조건에 맞는 데이터가 없습니다.</p>";
    } else {
        resultContainer.innerHTML = results.map(item => `
            <div class="product-item">
                <h3>${item.name}</h3>
                <p>모델 번호: ${item.modelNo}</p>
                <p>가격: ${item.price}</p>
                <p>브랜드: ${item.brand}</p>
                <p>색상: ${item.color}</p>
                <p>사이즈: ${item.size}</p>
                <p>성별: ${item.gender}</p>
                <p>작성자: ${item.createdBy}</p>
            </div>
        `).join("");
    }
}
