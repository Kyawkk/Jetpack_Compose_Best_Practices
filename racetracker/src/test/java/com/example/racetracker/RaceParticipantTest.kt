package com.example.racetracker

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RaceParticipantTest {
    private val participant = RaceParticipant(name = "Player One", progressIncrement = 1)

    @Test
    fun raceParticipant_RaceStarted_ProgressUpdate() = runTest {
        val expectedProgress = 1
        launch { participant.run() }
        advanceTimeBy(participant.progressDelayMillis)
        runCurrent()
        assertEquals(expectedProgress,participant.currentProgress)
    }

    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        launch { participant.run() }
        advanceTimeBy(participant.maxProgress * participant.progressDelayMillis)
        runCurrent()
        assertEquals(100, participant.currentProgress)
    }
}