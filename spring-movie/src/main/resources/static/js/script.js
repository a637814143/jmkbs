document.getElementById("rechargeBtn").addEventListener("click", function() {
    const username = document.getElementById("username").value.trim();
    const resultDiv = document.getElementById("result");

    if (!username) {
        resultDiv.textContent = "请输入用户名";
        resultDiv.className = "result error";
        resultDiv.style.display = "block";
        return;
    }

    // 调用后端充值接口
    fetch("http://localhost:8080/api/vip/recharge?username=" + username, {
        method: "POST"
    })
    .then(response => response.text())
    .then(data => {
        resultDiv.textContent = data;
        resultDiv.className = "result success";
        resultDiv.style.display = "block";
    })
    .catch(error => {
        resultDiv.textContent = "充值失败：" + error;
        resultDiv.className = "result error";
        resultDiv.style.display = "block";
    });
});