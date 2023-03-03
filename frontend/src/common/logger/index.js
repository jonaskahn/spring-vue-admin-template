import { createLogger } from 'vue-logger-plugin'

const logger = createLogger({
  enabled: true,
  consoleEnabled: true,
  callerInfo: true,
  level: import.meta.env.PROD ? 'debug' : 'error'
})

export default logger
