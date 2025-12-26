package org.example.conveyor

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ConveyorApplicationTest {
    @Test
    fun testRoot() =
        testApplication {
            application {
                configureRouting()
            }
            client.get("/").apply {
                assertEquals(HttpStatusCode.OK, status)
                assertEquals("Привет от Бондарчука!", bodyAsText())
            }
        }

    @Test
    fun testRootStatusCode() =
        testApplication {
            application {
                configureRouting()
            }
            client.get("/").apply {
                assertEquals(HttpStatusCode.OK, status)
            }
        }
}
