package com.example.iss

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.iss.db.AppDatabase
import com.example.iss.db.ISSPosition
import com.example.iss.db.ISSPositionDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseInstrumentedTest {
    private lateinit var db: AppDatabase
    private lateinit var issPositionDao: ISSPositionDao

    @Before
    fun setUp() {
        //val context = InstrumentationRegistry.getInstrumentation().targetContext
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        issPositionDao = db.issPositionDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadMultipleISSPositions() = runBlocking {
        issPositionDao.insert(ISSPosition(1, 1.0, 1.0))
        issPositionDao.insert(ISSPosition(1, 2.0, 2.0))
        issPositionDao.insert(ISSPosition(1, 3.0, 3.0))
        val issPositions = issPositionDao.getAll()
        Assert.assertTrue(issPositions.size == 3)
    }

    @Test
    fun writeAndReadISSPosition() = runBlocking {
        val issPosition = ISSPosition(1, 1.0, 1.0)
        issPositionDao.insert(issPosition)
        val issPositions = issPositionDao.getAll()
        Assert.assertTrue(issPositions.contains(issPosition))
    }

    @Test
    fun deleteISSPosition() = runBlocking {
        val issPosition = ISSPosition(1, 1.0, 1.0)
        issPositionDao.insert(issPosition)
        issPositionDao.delete(issPosition)
        val issPositions = issPositionDao.getAll()
        Assert.assertFalse(issPositions.contains(issPosition))
    }
}
