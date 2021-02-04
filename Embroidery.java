import tester.Tester;

interface IMotif {
  
  //to return the difficulty
  double giveDiff();
  
  // to return the name of the motif
  String giveName();
  
  //to create the starting words
  String startSentence();
  
}

class CrossStitchMotif implements IMotif {
  String description;
  double difficulty;
  
  CrossStitchMotif (String description, double difficulty){
    this.description = description;
    this.difficulty = difficulty;
  }

  @Override
  public double giveDiff() {
    return difficulty;
  }
  
  @Override
  public String giveName() {
    return ", " + description + " (cross stitch)";
  }

  @Override
  public String startSentence() {
   return description + " (cross stitch)";
  }
}
  


class ChainStitchMotif implements IMotif {
  String description;
  double difficulty;
  
  ChainStitchMotif (String description, double difficulty){
    this.description = description;
    this.difficulty = difficulty;
  }

  @Override
  public double giveDiff() {
    return difficulty;
  }
  
  @Override
  public String giveName() {
    return  ", " + description + " (chain stitch)";
  }

  @Override
  public String startSentence() {
    return description + " (chain stitch)";
  }
  

}


interface ILoMotif {
 
  //to calculate the average difficulty of a series of motifs
  double averageDiff();
  
  //to calculate the sum of the difficulty
  double sumDiff();
  
  //to calculate how many motifs are in a list
  int totalMotifs();
  
  // to create the name of the list
  String allNames();
  
  // to add the names and types of the stitch
  String stitchNames();
}

class MtLoMotif implements ILoMotif {
  
  MtLoMotif(){}

  @Override
  public double averageDiff() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double sumDiff() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int totalMotifs() {
    // TODO Auto-generated method stub
    return 0;
  }
  
  @Override
  public String allNames() {
    return null;
  }

  @Override
  public String stitchNames() {
    
    return ".";
  }
  
  
}

class ConsLoMotif implements ILoMotif {
  String description;
  IMotif first;
  ILoMotif rest;
  
  ConsLoMotif (String description, IMotif first, ILoMotif rest){
    this.description = description;
    this.first = first;
    this.rest = rest;
  }

  @Override
  public double averageDiff() {
    return this.sumDiff() / this.totalMotifs();
  }

  @Override
  public double sumDiff() {
    return first.giveDiff() + rest.sumDiff();
  }

  @Override
  public int totalMotifs() {
    return 1 + rest.totalMotifs();
  }
  
  @Override
  public String allNames() {
    return this.description + ": " + first.startSentence() + rest.stitchNames();
  }

  @Override
  public String stitchNames() {
    return first.giveName() + rest.stitchNames();
  
  }
}

class ExamplesEmbroidery{
  IMotif bird = new CrossStitchMotif("bird", 4.5);
  IMotif tree = new ChainStitchMotif("tree", 3.0);
  IMotif rose = new CrossStitchMotif("rose", 5.0);
  IMotif poppy = new ChainStitchMotif("poppy", 4.75);
  IMotif daisy = new CrossStitchMotif("daisy", 3.2);
  
  ILoMotif mtPattern = new MtLoMotif();
  ILoMotif flowers = new ConsLoMotif ("flowers", this.daisy,
      new ConsLoMotif("second level", this.poppy,
          new ConsLoMotif("base", this.rose, this.mtPattern)));
  ILoMotif pillowCover = new ConsLoMotif("pillow cover", this.bird, 
      new ConsLoMotif("base", this.tree, this.flowers));
  
  
  //test method averageDiff
  boolean testAverageDiff(Tester t) {
    return t.checkExpect(this.flowers.averageDiff(), 4.316666666666666) &&
           t.checkExpect(this.pillowCover.averageDiff(), 4.09) &&
           t.checkExpect(this.flowers.allNames(), "flowers: daisy (cross stitch), poppy (chain stitch), rose (cross stitch).");
  }
}