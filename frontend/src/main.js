import { createApp } from 'vue'
import App from './App.vue'
import { components, directives, uses } from '@/registry'
import '@/assets/styles.scss'
export const app = createApp(App)

uses(app)
components(app)
directives(app)

app.config.productionTip = false
app.mount('#app')
