import { createApp } from 'vue';
import { createPinia } from 'pinia';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

// 共享设计令牌（糖果橙/森林绿/星空蓝 + ZCOOL KuaiLe），与 ui-kit 同源
import '@paradise/design-tokens/dist/index.css';

import App from './App.vue';
import router from './router';
import { setupRequest } from './utils/request';

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(ElementPlus);
setupRequest(); // 注入 JWT、401/403 拦截
app.mount('#app');
