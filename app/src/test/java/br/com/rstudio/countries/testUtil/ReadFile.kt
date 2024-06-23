package br.com.rstudio.countries.testUtil

import java.io.FileNotFoundException
import java.net.URL

fun readFile(path: String): String {
  val content: URL? = ClassLoader.getSystemClassLoader().getResource(path)
  return content?.readText() ?: throw FileNotFoundException("file path: $path was not found")
}
