@file:JvmName("Main")
package org.hummingbirdlang

import java.io.File
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.source.Source
import com.oracle.truffle.api.vm.PolyglotEngine

fun main(args : Array<String>) {
  println("(running on ${Truffle.getRuntime().getName()}")

  val engine = PolyglotEngine.newBuilder().setIn(System.`in`).setOut(System.`out`).build()
  assert(engine.getLanguages().containsKey(HBLanguage.MIME_TYPE))

  val source = Source.newBuilder(File(args[0])).build()
  engine.eval(source)
}
