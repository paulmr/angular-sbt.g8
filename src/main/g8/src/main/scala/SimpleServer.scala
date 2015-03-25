import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ResourceHandler;

import org.eclipse.jetty.util.log.{Log => JettyLog, Slf4jLog}

import org.slf4j.LoggerFactory

object SimpleServer {

  import scala.language.implicitConversions

  val log = LoggerFactory.getLogger("simpleserver")
  // JettyLog.setLog(new Slf4jLog)
  // JettyLog.getLog.info("PMR_TEST")
  log.info("PMRTEST straight to SLF4j")

  implicit def opt(s: String): Option[String] =
    if(s == null) None else Some(s)

  def parseArgs(args: Array[String]): Map[String, String] = {
    val patt = """([A-Za-z]+)\s*=\s*(.*)""".r
    val settings = args collect {
      case patt(key, value) => key -> value
    }
    Map(settings: _*)
  }

  def makeHandler(path: String) = {
    val h = new ResourceHandler
    h.setDirectoriesListed(true)
    h.setResourceBase(path)
    h
  }

  def main(args: Array[String]) = {
    val config = parseArgs(args)
    val path = config.get("path").getOrElse(".")
    val port = config.get("port").getOrElse("8080").toInt

    log.info(s"running simple web server on port $port, server path $path")
    log.info(s"Logging propery is ${System.getProperty("org.slf4j.simpleLogger.logFile")}")

    val server = new Server(port)
    server.setHandler(makeHandler(path))

    server.start();
    server.join();
  }
}
