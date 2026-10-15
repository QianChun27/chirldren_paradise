/** @type {import('tailwindcss').Config} */
// 与 @paradise/design-tokens 同源：糖果橙/森林绿/星空蓝 语义色 + 童趣字体。
// 注意：关闭 preflight 避免覆盖 Element Plus 样式（见 ui-kit/index.html）。
export default {
  corePlugins: { preflight: false },
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
    // 共享组件包内的模板也纳入扫描
    './node_modules/@paradise/ui-components/**/*.{vue,js,ts}',
  ],
  theme: {
    extend: {
      colors: {
        candy: { DEFAULT: '#FF8A5B', light: '#FFB999', soft: '#FFF1EA', dark: '#E2733F' },
        sky: '#5BC8FF',
        mint: '#6FCF97',
        sun: '#FFC861',
        grape: '#B79CFF',
        rose: '#FF6B6B',
      },
      fontFamily: { kid: ['ZCOOL KuaiLe', 'PingFang SC', 'Microsoft YaHei', 'sans-serif'] },
      borderRadius: { pill: '999px' },
    },
  },
  plugins: [],
};
