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
    private val typed = StringBuilder()

    private val RAINBOW_CODE = -101
    private val HEART_CODE = -102
    private val UNICORN_CODE = -103
    private val FLAG_CODE = -104
    private val HEART_RAINBOW_CODE = -105

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
            Keyboard.KEYCODE_DONE -> ic.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER))
            RAINBOW_CODE -> ic.commitText("\uD83C\uDF08", 1)
            HEART_CODE -> ic.commitText("\u2764\uFE0F", 1)
            UNICORN_CODE -> ic.commitText("\uD83E\uDD84", 1)
            FLAG_CODE -> ic.commitText("\uD83C\uDFF3\uFE0F\u200D\uD83C\uDF08", 1)
            HEART_RAINBOW_CODE -> ic.commitText("\u2764\uFE0F\u200D\uD83C\uDF08", 1)
            else -> {
                val code = primaryCode.toChar()
                ic.commitText(code.toString(), 1)
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

    private fun checkForConfetti() {
        val word = typed.toString().lowercase()
        val keywords = listOf("love", "gay", "pride", "lgbti", "hrdosť", "hrdost", "radosť", "radost")
        if (keywords.any { word.endsWith(it) }) {
            Toast.makeText(this, "\uD83C\uDF89", Toast.LENGTH_SHORT).show()
            typed.clear()
        }
    }
}
