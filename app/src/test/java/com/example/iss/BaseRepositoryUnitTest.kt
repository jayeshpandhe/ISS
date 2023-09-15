package com.example.iss

import com.example.iss.api.Api
import com.example.iss.db.ISSPositionDao
import com.example.iss.model.Astronaut
import com.example.iss.model.AstronautsResponse
import com.example.iss.model.ISS
import com.example.iss.model.Position
import com.example.iss.repository.BaseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class BaseRepositoryUnitTest {
    companion object {
        const val TIMESTAMP = 1L
        const val LATITUDE = 2.0
        const val LONGITUDE = 3.0

        const val ASTRONAUT_NUMBER = 2
        const val ASTRONAUT1_CRAFT = "craft1"
        const val ASTRONAUT2_CRAFT = "craft2"

        const val ASTRONAUT1_NAME = "name1"
        const val ASTRONAUT2_NAME = "name2"
    }

    private val position = Position(LATITUDE, LONGITUDE)
    private val astronaut1 = Astronaut(ASTRONAUT1_CRAFT, ASTRONAUT1_NAME)
    private val astronaut2 = Astronaut(ASTRONAUT2_CRAFT, ASTRONAUT2_NAME)
    private val iss = ISS(timeStamp = TIMESTAMP, position = position)

    private lateinit var repository: BaseRepository

    @Before
    fun setUp() = runBlocking {
        val api = mock(Api::class.java)
        val astronauts = mutableListOf(astronaut1, astronaut2)
        val astronautsResponse = AstronautsResponse(number = ASTRONAUT_NUMBER, people = astronauts)
        `when`(api.getAstronauts()).thenReturn(astronautsResponse)
        `when`(api.getISSPosition()).thenReturn(iss)

        val issPositionDao = mock(ISSPositionDao::class.java)
        repository = BaseRepository(api, issPositionDao)
    }

    @Test
    fun validateGetAstronauts() = runBlocking {
        val astronaut = repository.getAstronauts()
        assertNotNull(astronaut)
        assertTrue(astronaut.number == ASTRONAUT_NUMBER)
        assertEquals(astronaut.people[0].craft, ASTRONAUT1_CRAFT)
        assertEquals(astronaut.people[1].craft, ASTRONAUT2_CRAFT)
        assertEquals(astronaut.people[0].name, ASTRONAUT1_NAME)
        assertEquals(astronaut.people[1].name, ASTRONAUT2_NAME)
    }

    @Test
    fun validateGetISS() = runBlocking {
        val iss = repository.getISS()
        assertNotNull(iss)
        assertTrue(iss.position.latitude == LATITUDE)
        assertTrue(iss.position.longitude == LONGITUDE)
    }
}