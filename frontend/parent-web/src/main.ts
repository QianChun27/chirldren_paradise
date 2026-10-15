import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { NavBar, Button, Icon, Switch, Slider, Cell, CellGroup } from 'vant';
import 'vant/lib/index.css';

// 共享设计令牌（与儿童端/PC 同源）
import '@paradise/design-tokens/dist/index.css';

import App from './App.vue';
import router from './router';
import { setupRequest } from './utils/request';

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(NavBar).use(Button).use(Icon).use(Switch).use(Slider).use(Cell).use(CellGroup);
setupRequest();
app.mount('#app');
