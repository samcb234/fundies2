import tester.Tester;
import java.awt.Color;


interface IPaint {
  
  //to compute the total number of paints used to create a color
  int countPaints();
  
  // to count instances of mixing
  int countMixes();
  
  //to compute the final color of the paint
  Color getFinalColor();
  
  // to get the red value
  int getRed();
  
  // to get the green value
  int getGreen();
  
  // to get the blue value
  int getBlue();
  
  
}

class Solid implements IPaint{
  String name;
  Color color;
  
  Solid(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  @Override
  public int countPaints() {
    return 1;
  }

  @Override
  public Color getFinalColor() {
    return this.color;
  }

  @Override
  public int getRed() {
    return this.color.getRed() / 2;
  }

  @Override
  public int getGreen() {
    return this.color.getGreen() /2;
  }

  @Override
  public int getBlue() {
    return this.color.getBlue() / 2;
  }

  @Override
  public int countMixes() {
    return 0;
  }

  
}

class Combo implements IPaint {
  String name;
  IMixture operation;
  
  Combo (String name, IMixture operation) {
    this.name = name;
    this.operation = operation;
  }

  @Override
  public int countPaints() {
    return this.operation.returnInt();
  }

  @Override
  public Color getFinalColor() {
    return this.operation.getFinalColor();
  }

  @Override
  public int getRed() {
    return this.operation.getFinalColor().getRed() / 2;
  }

  @Override
  public int getGreen() {
    return this.operation.getFinalColor().getGreen() / 2;
  }

  @Override
  public int getBlue() {
    return this.operation.getFinalColor().getBlue() / 2;
  }

  @Override
  public int countMixes() {
    return this.operation.countMixes();
  }

  
}

interface IMixture {
  //to return an integer to represent a paint used
  int returnInt();
  
  int countMixes();
  
  Color getFinalColor();
  
}

class Darken implements IMixture {
  IPaint color;
  Darken(IPaint color){
    this.color = color;
  }
  @Override
  public int returnInt() {
    return 1 + this.color.countPaints();
  }
  @Override
  public Color getFinalColor() {
    return this.color.getFinalColor().darker();
  }
  @Override
  public int countMixes() {
    return 1 + this.color.countMixes();
  }
}

class Brighten implements IMixture {
  IPaint color;
  Brighten(IPaint color){
    this.color = color;
  }
  @Override
  public int returnInt() {
    return 1 + this.color.countPaints();
  }
  @Override
  public Color getFinalColor() {
    return this.color.getFinalColor().brighter();
  }
  @Override
  public int countMixes() {
    return 1 + this.color.countMixes();
  }
  
}

class Blend implements IMixture {
  IPaint color1;
  IPaint color2;
  Blend(IPaint color1, IPaint color2){
    this.color1 = color1;
    this.color2 = color2;
  }
  @Override
  public int returnInt() {
    return this.color1.countPaints() + this.color2.countPaints();
  }
  @Override
  public Color getFinalColor() {
    return new Color(color1.getRed()+ color2.getRed(), 
        color1.getGreen()+ color2.getGreen(), 
        color1.getBlue()+ color2.getBlue());
  }
  @Override
  public int countMixes() {
    return 1 + this.color1.countMixes() +this.color2.countMixes();
  }
}

class ExamplesPaint {
  IPaint green = new Solid ("blue", Color.green);
  IPaint red = new Solid ("blue", Color.red);
  IPaint blue = new Solid ("blue", Color.blue);
  IMixture purpleBlend = new Blend (this.red, this.blue);
  IPaint purple = new Combo ("purple", purpleBlend);
  IMixture purpleDarken = new Darken (this.purple);
  IPaint darkPurple = new Combo ("dark purple", purpleDarken);
  IMixture khakiBlend = new Blend (this.red, this.green);
  IPaint khaki = new Combo ("khaki", khakiBlend);
  IMixture khakiBrighten = new Brighten (this.khaki);
  IPaint yellow = new Combo ("yellow", khakiBrighten);
  IMixture mauveBlend = new Blend (this.purple, this.khaki);
  IPaint mauve = new Combo ("mauve", mauveBlend);
  IMixture pinkBrighten = new Brighten (this.mauve);
  IPaint pink = new Combo ("pink", pinkBrighten);
  IMixture coralBlend = new Blend (this.pink, this.khaki);
  IPaint coral = new Combo ("coral", coralBlend);
  IMixture tealBlend = new Blend (this.green, this.blue);
  IPaint teal = new Combo ("teal", tealBlend);
  IMixture redDarken = new Darken (this.red);
  IPaint crimson = new Combo ("crimson", this.redDarken);
  IMixture blueBrighten = new Brighten (this.blue);
  IPaint skyBlue = new Combo ("skyblue", this.blueBrighten);
  
  
  boolean testCountPaints (Tester t) {
    return t.checkExpect(this.green.countPaints(), 1) &&
        t.checkExpect(this.skyBlue.countPaints(), 2) &&
        t.checkExpect(this.yellow.countPaints(), 3) &&
        t.checkExpect(this.khaki.getFinalColor(), new Color(127, 127, 0)) &&
        t.checkExpect(this.coral.countMixes(), 6);
  }
}