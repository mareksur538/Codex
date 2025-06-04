package com.queerty.keyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Toast

class QueertyKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var kv: KeyboardView
    private lateinit var keyboard: Keyboard
    private var caps = false
    private var typed = StringBuilder()

    override fun onCreateInputView(): View {
        kv = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyboard = Keyboard(this, R.xml.keyboard_layout)
        kv.keyboard = keyboard
        kv.setOnKeyboardActionListener(this)
        return kv
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val ic: InputConnection = currentInputConnection
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
            Keyboard.KEYCODE_SHIFT -> handleCapsLove()
            Keyboard.KEYCODE_DONE -> ic.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER))
            else -> {
                val code = primaryCode.toChar()
                val text = if (caps) code.uppercaseChar().toString() else code.toString()
                ic.commitText(text, 1)
                typed.append(code)
                checkForConfetti()
            }
        }
    }

    override fun onPress(primaryCode: Int) {}

    override fun onRelease(primaryCode: Int) {}

    override fun onText(text: CharSequence?) {}

    override fun swipeDown() {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeUp() {}

    private fun handleCapsLove() {
        caps = !caps
        Toast.makeText(this, "\uD83D\uDC95", Toast.LENGTH_SHORT).show()
    }

    private fun checkForConfetti() {
        val word = typed.toString().lowercase()
        if (word.endsWith("love") || word.endsWith("gay") || word.endsWith("pride") || word.endsWith("radosť")) {
            Toast.makeText(this, "\uD83C\uDF89", Toast.LENGTH_SHORT).show()
            typed.clear()
        }
    }
}
