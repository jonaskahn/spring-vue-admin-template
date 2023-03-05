import { createLogger, StringifyAndParseObjectsHook } from 'vue-logger-plugin'

const logger = createLogger({
  enabled: true,
  consoleEnabled: true,
  callerInfo: import.meta.env.DEV,
  level: import.meta.env.DEV ? 'debug' : 'error',
  beforeHooks: [StringifyAndParseObjectsHook]
})

export default logger
