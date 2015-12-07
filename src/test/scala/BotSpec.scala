import org.specs2.mutable.Specification

class BotSpec extends Specification {

  "The Bot" should {
    "respond with a Status" in {
      new Bot().respond("") must be equalTo "Status(text=Hello!)"
    }
  }
}
