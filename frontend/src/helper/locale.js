import constants from "@/constants"
import i18n from "@/i18n"

export function getCurrentLocale() {
  return localStorage.getItem(constants.STORAGE.CURRENT_LOCALE) ?? "en"
}

export function switchLanguage(locale) {
  i18n.global.locale.value = locale
  document.querySelector("html").setAttribute("lang", locale)
  localStorage.setItem(constants.STORAGE.CURRENT_LOCALE, locale)
}

const translate = i18n.global.t

export { translate }
