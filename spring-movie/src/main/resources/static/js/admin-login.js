document.addEventListener('DOMContentLoaded', () => {
  // DOM元素
  const loginForm = document.getElementById('loginForm');
  const usernameInput = document.getElementById('username');
  const passwordInput = document.getElementById('password');
  const togglePwdBtn = document.getElementById('togglePwd');
  const loginBtn = document.getElementById('loginBtn');
  const usernameError = document.getElementById('usernameError');
  const passwordError = document.getElementById('passwordError');

  // 密码显示/隐藏切换
  togglePwdBtn.addEventListener('click', () => {
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);
    
    // 切换图标
    const icon = togglePwdBtn.querySelector('i');
    if (type === 'text') {
      icon.classList.remove('fa-eye-slash');
      icon.classList.add('fa-eye');
    } else {
      icon.classList.remove('fa-eye');
      icon.classList.add('fa-eye-slash');
    }
  });

  // 表单验证
  function validateForm() {
    let isValid = true;
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    // 验证用户名
    if (!username) {
      showError(usernameInput, usernameError, '用户名不能为空');
      isValid = false;
    } else if (username.length < 3 || username.length > 20) {
      showError(usernameInput, usernameError, '用户名长度需在3-20个字符之间');
      isValid = false;
    } else {
      hideError(usernameInput, usernameError);
    }

    // 验证密码
    if (!password) {
      showError(passwordInput, passwordError, '密码不能为空');
      isValid = false;
    } else if (password.length < 6 || password.length > 20) {
      showError(passwordInput, passwordError, '密码长度需在6-20个字符之间');
      isValid = false;
    } else {
      hideError(passwordInput, passwordError);
    }

    return isValid;
  }

  // 显示错误提示
  function showError(input, errorElement, message) {
    const formGroup = input.closest('.form-group');
    formGroup.classList.add('error');
    errorElement.textContent = message;
  }

  // 隐藏错误提示
  function hideError(input, errorElement) {
    const formGroup = input.closest('.form-group');
    formGroup.classList.remove('error');
    errorElement.textContent = '';
  }

  // 输入框实时验证
  [usernameInput, passwordInput].forEach(input => {
    input.addEventListener('input', () => {
      const formGroup = input.closest('.form-group');
      if (formGroup.classList.contains('error')) {
        validateForm();
      }
    });
  });

  // 登录表单提交
  loginForm.addEventListener('submit', (e) => {
    e.preventDefault();

    // 表单验证
    if (!validateForm()) return;

    // 模拟登录请求
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    // 禁用按钮，显示加载状态
    loginBtn.disabled = true;
    loginBtn.classList.add('loading');
    loginBtn.innerHTML = '<i class="fas fa-spinner"></i> 登录中...';

    // 模拟AJAX请求（实际项目中替换为真实接口）
    setTimeout(() => {
      // 模拟登录成功（实际项目中根据后端返回结果判断）
      if (username === 'admin' && password === '123456') {
        // 登录成功：存储token，跳转到admin-homepage
        localStorage.setItem('adminToken', 'fake-admin-token-123456'); // 模拟存储token
        alert('登录成功，即将跳转到管理员首页！');
        window.location.href = '/admin-homepage.html'; // 跳转到admin-homepage
      } else {
        // 登录失败：显示错误提示
        alert('用户名或密码错误，请重新输入！');
        // 恢复按钮状态
        loginBtn.disabled = false;
        loginBtn.classList.remove('loading');
        loginBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> 登录';
        // 聚焦密码框
        passwordInput.focus();
      }
    }, 1500); // 模拟网络延迟1.5秒
  });
});