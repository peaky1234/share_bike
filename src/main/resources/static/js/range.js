// 获取滑动选择器实例
const sliderEl = document.querySelector(
    ".speed_control"
  );
  // 获取数值显示容器实例


  // 监听滑动事件
  sliderEl.addEventListener("input", () => {
    localStorage.setItem('speed',sliderEl.value);
    updateSpeed()
  });



