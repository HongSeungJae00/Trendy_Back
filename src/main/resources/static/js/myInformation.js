document.addEventListener('DOMContentLoaded', () => {
    // 사용자 정보 로드
    fetch('/api/user/me')
        .then(response => response.json())
        .then(data => {
            document.getElementById('email').value = data.email || '';
            document.getElementById('phone').value = data.phone || '기입 필요';
            document.getElementById('shoeSize').value = data.shoeSize || '기입 필요';
        })
        .catch(err => console.error('사용자 정보를 불러오지 못했습니다.', err));

    // 변경 버튼 클릭 이벤트
    const editButtons = document.querySelectorAll('.edit-button');
    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            const field = button.dataset.field;
            const value = document.getElementById(field).value;

            if (!value) {
                alert(`${field}를 입력해주세요.`);
                return;
            }

            // 서버에 업데이트 요청
            fetch(`/api/user/update`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ [field]: value })
            })
                .then(response => {
                    if (response.ok) {
                        alert('정보가 성공적으로 변경되었습니다.');
                    } else {
                        alert('정보 변경에 실패했습니다.');
                    }
                })
                .catch(err => console.error('정보 변경 중 오류가 발생했습니다.', err));
        });
    });
});
