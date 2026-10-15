import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { Tabbar, TabbarItem, Button, Icon } from 'vant';
import 'vant/lib/index.css';

// 共享设计令牌（与 PC 管理台同源）
import '@paradise/design-tokens/dist/index.css';

import App from './App.vue';
import router from './router';
import { setupRequest } from './utils/request';

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(Button).use(Icon).use(Tabbar).use(TabbarItem);
setupRequest();
app.mount('#app');
