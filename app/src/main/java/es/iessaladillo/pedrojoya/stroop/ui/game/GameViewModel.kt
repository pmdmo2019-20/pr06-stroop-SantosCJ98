package es.iessaladillo.pedrojoya.stroop.ui.game

import android.app.Application
import android.graphics.Color
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import kotlin.concurrent.thread
import kotlin.random.Random


class GameViewModel(val rankingDao: RankingDao, val application: Application) : ViewModel() {

    @Volatile
    private var isGameFinished: Boolean = false
    @Volatile
    private var currentWordMillis: Int = 0
    @Volatile
    private var millisUntilFinished: Int = 0
    private val handler: Handler = Handler()

    // TODO

    val listPalabras = listOf("Green", "Yellow", "Blue", "Red", "Black", "Magenta")

    val listColores = listOf(Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED, Color.BLACK, Color.MAGENTA)

    val bar: MutableLiveData<Int> = MutableLiveData(millisUntilFinished)

    val player: MutableLiveData<Player> = MutableLiveData()

    val palabra: MutableLiveData<String> = MutableLiveData(listPalabras.get(Random.nextInt(6)))

    val color: MutableLiveData<Int> = MutableLiveData(listColores.get(Random.nextInt(6)))

    val puntos: MutableLiveData<Int> = MutableLiveData(0)

    val words: MutableLiveData<Int> = MutableLiveData(0)

    val correct: MutableLiveData<Int> = MutableLiveData(0)

    val incorrect: MutableLiveData<Int> = MutableLiveData(0)

    val modo: MutableLiveData<String> = MutableLiveData()

    val tiempo: MutableLiveData<Int> = MutableLiveData()

    val finish: MutableLiveData<Boolean> = MutableLiveData(false)

    val attempts: MutableLiveData<Int> = MutableLiveData()



    //Cada vez que se mueve el reloj. Se recibe el tiempo restante.
    private fun onGameTimeTick(millisUntilFinished: Int) {
        bar.value = millisUntilFinished
    }

    //Cuando se acaba el tiempo.

    fun onGameTimeFinish() {

        isGameFinished = true

        finish.value = true



    }

    //Siguiente palabra.
    fun nextWord() {

        palabra.value = listPalabras.get(Random.nextInt(6))
        color.value = listColores.get(Random.nextInt(6))
        words.value = words.value!!.plus(1)
    }

    //Es correcto

    fun checkRight() {
        currentWordMillis = 0

        val indexWord = listPalabras.indexOfFirst { it == palabra.value }
        val indexColor = listColores.indexOfFirst { it == color.value }

        if (indexColor == indexWord) {

            puntos.value = puntos.value!!.plus(10)
            correct.value = correct.value!!.plus(1)

        }

        else {

            incorrect.value = incorrect.value!!.plus(1)

            attempts.value = attempts.value!!.minus(1)



        }

        if (attempts.value as Int <= 0 && modo.value == "Attempts") {

            onGameTimeFinish()

        }

        else {

            nextWord()


        }



    }

    //Es incorrecto

    fun checkWrong() {
        currentWordMillis = 0

        val indexWord = listPalabras.indexOfFirst { it == palabra.value }
        val indexColor = listColores.indexOfFirst { it == color.value }

        if (indexColor != indexWord) {

            puntos.value = puntos.value!!.plus(10)
            correct.value = correct.value!!.plus(1)

        }

        else {

            incorrect.value = incorrect.value!!.plus(1)

            attempts.value = attempts.value!!.minus(1)


        }

        if (attempts.value as Int <= 0 && modo.value == "Attempts") {

            onGameTimeFinish()

        }

        else {

            nextWord()

        }
    }

    fun startGameThread(gameTime: Int, wordTime: Int) {
        millisUntilFinished = gameTime
        currentWordMillis = 0
        isGameFinished = false
        val checkTimeMillis: Int = wordTime / 2
        thread {
            try {
                while (!isGameFinished) {
                    Thread.sleep(checkTimeMillis.toLong())
                    // Check and publish on main thread.
                    handler.post {
                        if (!isGameFinished) {
                            if (currentWordMillis >= wordTime) {
                                currentWordMillis = 0
                                nextWord()
                            }
                            currentWordMillis += checkTimeMillis
                            millisUntilFinished -= checkTimeMillis
                            onGameTimeTick(millisUntilFinished)
                            if (millisUntilFinished <= 0) {
                                onGameTimeFinish()
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        isGameFinished = true
    }

}