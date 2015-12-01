
class ControlFunctionFactory {
    def create = new Bot respond _
}

class Bot {
  def respond(input: String): String = "Status(text=HI)"
}