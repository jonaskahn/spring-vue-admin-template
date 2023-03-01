<script setup>
import RadioButton from 'primevue/radiobutton';
import Button from 'primevue/button';
import InputSwitch from 'primevue/inputswitch';
import Sidebar from 'primevue/sidebar';

import { ref } from 'vue';
import { useLayout } from '@/layout/composables/layout';

defineProps({
    simple: {
        type: Boolean,
        default: false
    }
});
const scales = ref([12, 13, 14, 15, 16]);
const visible = ref(false);

const { changeThemeSettings, setScale, layoutConfig } = useLayout();

const onConfigButtonClick = () => {
    visible.value = !visible.value;
};
const onChangeTheme = (theme, mode) => {
    const elementId = 'theme-css';
    const linkElement = document.getElementById(elementId);
    const cloneLinkElement = linkElement.cloneNode(true);
    const newThemeUrl = linkElement.getAttribute('href').replace(layoutConfig.theme.value, theme);
    cloneLinkElement.setAttribute('id', elementId + '-clone');
    cloneLinkElement.setAttribute('href', newThemeUrl);
    cloneLinkElement.addEventListener('load', () => {
        linkElement.remove();
        cloneLinkElement.setAttribute('id', elementId);
        changeThemeSettings(theme, mode === 'dark');
    });
    linkElement.parentNode.insertBefore(cloneLinkElement, linkElement.nextSibling);
};
const decrementScale = () => {
    setScale(layoutConfig.scale.value - 1);
    applyScale();
};
const incrementScale = () => {
    setScale(layoutConfig.scale.value + 1);
    applyScale();
};
const applyScale = () => {
    document.documentElement.style.fontSize = layoutConfig.scale.value + 'px';
};
</script>

<template>
    <button class="layout-config-button p-link" type="button" @click="onConfigButtonClick()">
        <i class="pi pi-cog"></i>
    </button>

    <Sidebar v-model:visible="visible" :transitionOptions="'.3s cubic-bezier(0, 0, 0.2, 1)'" class="layout-config-sidebar w-20rem" position="right">
        <h5>Scale</h5>
        <div class="flex align-items-center">
            <Button :disabled="layoutConfig.scale.value === scales[0]" class="p-button-text p-button-rounded w-2rem h-2rem mr-2" icon="pi pi-minus" type="button" @click="decrementScale()"></Button>
            <div class="flex gap-2 align-items-center">
                <i v-for="s in scales" :key="s" :class="{ 'text-primary-500': s === layoutConfig.scale.value }" class="pi pi-circle-fill text-300"></i>
            </div>
            <Button :disabled="layoutConfig.scale.value === scales[scales.length - 1]" class="p-button-text p-button-rounded w-2rem h-2rem ml-2" icon="pi pi-plus" pButton type="button" @click="incrementScale()"></Button>
        </div>

        <template v-if="!simple">
            <h5>Menu Type</h5>
            <div class="flex">
                <div class="field-radiobutton flex-1">
                    <RadioButton v-model="layoutConfig.menuMode.value" inputId="mode1" name="menuMode" value="static"></RadioButton>
                    <label for="mode1">Static</label>
                </div>

                <div class="field-radiobutton flex-1">
                    <RadioButton v-model="layoutConfig.menuMode.value" inputId="mode2" name="menuMode" value="overlay"></RadioButton>
                    <label for="mode2">Overlay</label>
                </div>
            </div>
        </template>

        <template v-if="!simple">
            <h5>Input Style</h5>
            <div class="flex">
                <div class="field-radiobutton flex-1">
                    <RadioButton v-model="layoutConfig.inputStyle.value" inputId="outlined_input" name="inputStyle" value="outlined"></RadioButton>
                    <label for="outlined_input">Outlined</label>
                </div>
                <div class="field-radiobutton flex-1">
                    <RadioButton v-model="layoutConfig.inputStyle.value" inputId="filled_input" name="inputStyle" value="filled"></RadioButton>
                    <label for="filled_input">Filled</label>
                </div>
            </div>

            <h5>Ripple Effect</h5>
            <InputSwitch v-model="layoutConfig.ripple.value"></InputSwitch>
        </template>

        <h5>Bootstrap</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('bootstrap4-light-blue', 'light')">
                    <img alt="Bootstrap Light Blue" class="w-2rem h-2rem" src="/layout/images/themes/bootstrap4-light-blue.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('bootstrap4-light-purple', 'light')">
                    <img alt="Bootstrap Light Purple" class="w-2rem h-2rem" src="/layout/images/themes/bootstrap4-light-purple.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('bootstrap4-dark-blue', 'dark')">
                    <img alt="Bootstrap Dark Blue" class="w-2rem h-2rem" src="/layout/images/themes/bootstrap4-dark-blue.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('bootstrap4-dark-purple', 'dark')">
                    <img alt="Bootstrap Dark Purple" class="w-2rem h-2rem" src="/layout/images/themes/bootstrap4-dark-purple.svg" />
                </button>
            </div>
        </div>

        <h5>Material Design</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('md-light-indigo', 'light')">
                    <img alt="Material Light Indigo" class="w-2rem h-2rem" src="/layout/images/themes/md-light-indigo.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('md-light-deeppurple', 'light')">
                    <img alt="Material Light DeepPurple" class="w-2rem h-2rem" src="/layout/images/themes/md-light-deeppurple.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('md-dark-indigo', 'dark')">
                    <img alt="Material Dark Indigo" class="w-2rem h-2rem" src="/layout/images/themes/md-dark-indigo.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('md-dark-deeppurple', 'dark')">
                    <img alt="Material Dark DeepPurple" class="w-2rem h-2rem" src="/layout/images/themes/md-dark-deeppurple.svg" />
                </button>
            </div>
        </div>

        <h5>Material Design Compact</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('mdc-light-indigo', 'light')">
                    <img alt="Material Light Indigo" class="w-2rem h-2rem" src="/layout/images/themes/md-light-indigo.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('mdc-light-deeppurple', 'light')">
                    <img alt="Material Light Deep Purple" class="w-2rem h-2rem" src="/layout/images/themes/md-light-deeppurple.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('mdc-dark-indigo', 'dark')">
                    <img alt="Material Dark Indigo" class="w-2rem h-2rem" src="/layout/images/themes/md-dark-indigo.svg" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('mdc-dark-deeppurple', 'dark')">
                    <img alt="Material Dark Deep Purple" class="w-2rem h-2rem" src="/layout/images/themes/md-dark-deeppurple.svg" />
                </button>
            </div>
        </div>

        <h5>Tailwind</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('tailwind-light', 'light')">
                    <img alt="Tailwind Light" class="w-2rem h-2rem" src="/layout/images/themes/tailwind-light.png" />
                </button>
            </div>
        </div>

        <h5>Fluent UI</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('fluent-light', 'light')">
                    <img alt="Fluent Light" class="w-2rem h-2rem" src="/layout/images/themes/fluent-light.png" />
                </button>
            </div>
        </div>

        <h5>PrimeOne Design - 2022</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-light-indigo', 'light')">
                    <img alt="Lara Light Indigo" class="w-2rem h-2rem" src="/layout/images/themes/lara-light-indigo.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-light-blue', 'light')">
                    <img alt="Lara Light Blue" class="w-2rem h-2rem" src="/layout/images/themes/lara-light-blue.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-light-purple', 'light')">
                    <img alt="Lara Light Purple" class="w-2rem h-2rem" src="/layout/images/themes/lara-light-purple.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-light-teal', 'light')">
                    <img alt="Lara Light Teal" class="w-2rem h-2rem" src="/layout/images/themes/lara-light-teal.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-dark-indigo', 'dark')">
                    <img alt="Lara Dark Indigo" class="w-2rem h-2rem" src="/layout/images/themes/lara-dark-indigo.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-dark-blue', 'dark')">
                    <img alt="Lara Dark Blue" class="w-2rem h-2rem" src="/layout/images/themes/lara-dark-blue.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-dark-purple', 'dark')">
                    <img alt="Lara Dark Purple" class="w-2rem h-2rem" src="/layout/images/themes/lara-dark-purple.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('lara-dark-teal', 'dark')">
                    <img alt="Lara Dark Teal" class="w-2rem h-2rem" src="/layout/images/themes/lara-dark-teal.png" />
                </button>
            </div>
        </div>

        <h5>PrimeOne Design - 2021</h5>
        <div class="grid">
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('saga-blue', 'light')">
                    <img alt="Saga Blue" class="w-2rem h-2rem" src="/layout/images/themes/saga-blue.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('saga-green', 'light')">
                    <img alt="Saga Green" class="w-2rem h-2rem" src="/layout/images/themes/saga-green.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('saga-orange', 'light')">
                    <img alt="Saga Orange" class="w-2rem h-2rem" src="/layout/images/themes/saga-orange.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('saga-purple', 'light')">
                    <img alt="Saga Purple" class="w-2rem h-2rem" src="/layout/images/themes/saga-purple.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('vela-blue', 'dark')">
                    <img alt="Vela Blue" class="w-2rem h-2rem" src="/layout/images/themes/vela-blue.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('vela-green', 'dark')">
                    <img alt="Vela Green" class="w-2rem h-2rem" src="/layout/images/themes/vela-green.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('vela-orange', 'dark')">
                    <img alt="Vela Orange" class="w-2rem h-2rem" src="/layout/images/themes/vela-orange.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('vela-purple', 'dark')">
                    <img alt="Vela Purple" class="w-2rem h-2rem" src="/layout/images/themes/vela-purple.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('arya-blue', 'dark')">
                    <img alt="Arya Blue" class="w-2rem h-2rem" src="/layout/images/themes/arya-blue.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('arya-green', 'dark')">
                    <img alt="Arya Green" class="w-2rem h-2rem" src="/layout/images/themes/arya-green.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('arya-orange', 'dark')">
                    <img alt="Arya Orange" class="w-2rem h-2rem" src="/layout/images/themes/arya-orange.png" />
                </button>
            </div>
            <div class="col-3">
                <button class="p-link w-2rem h-2rem" @click="onChangeTheme('arya-purple', 'dark')">
                    <img alt="Arya Purple" class="w-2rem h-2rem" src="/layout/images/themes/arya-purple.png" />
                </button>
            </div>
        </div>
    </Sidebar>
</template>

<style lang="scss" scoped></style>
