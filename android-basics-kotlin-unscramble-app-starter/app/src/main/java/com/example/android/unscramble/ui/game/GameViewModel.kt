package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    private var _score = 0
    val score : Int
    get() = _score

    private var _currentWordCount = 0
    val currentWordCount : Int
    get() = _currentWordCount

    private lateinit var _currentScrambledWord : String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    /*
    * Read a new word from the word list and scramble it
    * if the word is not in the list it added to the list and increase the counter
    * if the word in on the list call it self to get a new word
    */
    private fun getNextWord() {
        // Get a new word
        currentWord = allWordsList.random()

        // Scramble the new word
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }

    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /*
    * Increase the score by the pre-define amount SCORE_INCREASE
     */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Validate is the word inputed by the use is correct
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

}