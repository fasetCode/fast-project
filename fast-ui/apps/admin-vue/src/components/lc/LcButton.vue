<script setup lang="ts">
import {computed, ref} from "vue";

const props = withDefaults(defineProps<{
  label?: string
  btnType?: 'primary' | 'default' | 'dashed' | 'danger'
  size?: 'small' | 'middle' | 'large'
  block?: boolean
  disabled?: boolean
}>(), {
  label: 'Button',
  btnType: 'primary',
  size: 'middle',
  block: false,
  disabled: false
})

const emit = defineEmits<{
  click: [event: MouseEvent | TouchEvent]
}>()

const isPressed = ref(false)
const isFocused = ref(false)

const buttonClasses = computed(() => {
  const classes = [`lc-btn lc-btn-${props.btnType} lc-btn-${props.size}`]
  if (props.block) classes.push('lc-btn-block')
  if (isPressed.value) classes.push('lc-btn-pressed')
  if (isFocused.value) classes.push('lc-btn-focused')
  if (props.disabled) classes.push('lc-btn-disabled')
  return classes.join(' ')
})

const handleMouseDown = () => {
  if (!props.disabled) {
    isPressed.value = true
  }
}

const handleMouseUp = () => {
  isPressed.value = false
}

const handleMouseLeave = () => {
  isPressed.value = false
}

const handleFocus = () => {
  if (!props.disabled) {
    isFocused.value = true
  }
}

const handleBlur = () => {
  isFocused.value = false
}

const handleTouchStart = () => {
  if (!props.disabled) {
    isPressed.value = true
  }
}

const handleTouchEnd = () => {
  isPressed.value = false
}

const handleClick = (event: MouseEvent | TouchEvent) => {
  if (!props.disabled) {
    emit('click', event)
  }
}
</script>

<template>
<!--  <div class="lc-button-wrap">-->

<!--  </div>-->
  <button
      :class="buttonClasses"
      :disabled="disabled"
      @mousedown="handleMouseDown"
      @mouseup="handleMouseUp"
      @mouseleave="handleMouseLeave"
      @focus="handleFocus"
      @blur="handleBlur"
      @touchstart="handleTouchStart"
      @touchend="handleTouchEnd"
      @touchcancel="handleTouchEnd"
      @click="handleClick"
  >
    <slot/>
  </button>
</template>

<style scoped>
.lc-btn {
  width: 100%;
  outline: none;
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-weight: 400;
  white-space: nowrap;
  text-align: center;
  background-image: none;
  background-color: transparent;
  border: 1px solid transparent;
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  user-select: none;
  touch-action: manipulation;
  border-radius: 6px;
  box-sizing: border-box;
  font-family: inherit;
}

/* Active Animation */
.lc-btn:not(.lc-btn-disabled).lc-btn-pressed {
  transform: scale(0.96);
}

/* Sizes */
.lc-btn-small {
  height: 24px;
  padding: 0 7px;
  font-size: 14px;
  border-radius: 4px;
}

.lc-btn-middle {
  height: 32px;
  padding: 0 15px;
  font-size: 14px;
}

.lc-btn-large {
  height: 40px;
  padding: 0 15px;
  font-size: 16px;
  border-radius: 8px;
}

/* Types */
.lc-btn-default {
  background-color: #ffffff;
  border-color: #d9d9d9;
  color: rgba(0, 0, 0, 0.88);
  box-shadow: 0 2px 0 rgba(0, 0, 0, 0.02);
}
.lc-btn-default:hover, .lc-btn-default.lc-btn-focused {
  color: #1677ff;
  border-color: #1677ff;
}
.lc-btn-default.lc-btn-pressed {
  color: #0958d9;
  border-color: #0958d9;
  background-color: #f5f5f5;
}

.lc-btn-primary {
  color: #fff;
  background-color: #1677ff;
  box-shadow: 0 2px 0 rgba(5, 145, 255, 0.1);
}
.lc-btn-primary:hover, .lc-btn-primary.lc-btn-focused {
  background-color: #4096ff;
}
.lc-btn-primary.lc-btn-pressed {
  background-color: #0958d9;
}

.lc-btn-dashed {
  background-color: #ffffff;
  border-color: #d9d9d9;
  color: rgba(0, 0, 0, 0.88);
  border-style: dashed;
  box-shadow: 0 2px 0 rgba(0, 0, 0, 0.02);
}
.lc-btn-dashed:hover, .lc-btn-dashed.lc-btn-focused {
  color: #1677ff;
  border-color: #1677ff;
}
.lc-btn-dashed.lc-btn-pressed {
  color: #0958d9;
  border-color: #0958d9;
  background-color: #f5f5f5;
}

.lc-btn-danger {
  color: #fff;
  background-color: #ff4d4f;
  box-shadow: 0 2px 0 rgba(255, 38, 5, 0.06);
}
.lc-btn-danger:hover, .lc-btn-danger.lc-btn-focused {
  background-color: #ff7875;
}
.lc-btn-danger.lc-btn-pressed {
  background-color: #d9363e;
}

/* Block */
.lc-btn-block {
  width: 100%;
}

/* Disabled State */
.lc-btn-disabled,
.lc-btn-disabled:hover,
.lc-btn-disabled:focus,
.lc-btn-disabled:active {
  cursor: not-allowed;
  box-shadow: none;
}

.lc-btn-default.lc-btn-disabled,
.lc-btn-dashed.lc-btn-disabled {
  color: rgba(0, 0, 0, 0.25);
  background-color: #f5f5f5;
  border-color: #d9d9d9;
}

.lc-btn-primary.lc-btn-disabled,
.lc-btn-danger.lc-btn-disabled {
  color: rgba(255, 255, 255, 0.6);
  background-color: #e6e6e6;
  border-color: transparent;
}
.lc-btn-primary.lc-btn-disabled {
  background-color: #a8cbfb;
}
.lc-btn-danger.lc-btn-disabled {
  background-color: #ffb3b4;
}
</style>
