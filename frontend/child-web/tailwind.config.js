/** @type {import('tailwindcss').Config} */
// 移动端沿用与 PC 同源的设计令牌（糖果橙/森林绿/星空蓝 + ZCOOL KuaiLe）。
export default {
  corePlugins: { preflight: false },
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
    './node_modules/@paradise/ui-components/**/*.{vue,js,ts}',
    './node_modules/vant/**/*.vue',
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
