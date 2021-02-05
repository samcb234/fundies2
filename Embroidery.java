import tester.Tester;

interface IMotif {
  /*methods:
   * this.giveDiff()
   * this.giveName()
   * this.startSentence()
   */
  //to return the difficulty of a motif
  double giveDiff();
  
  // to return the name of a motif
  String giveName();
  
  //to create the starting words
  String startSentence();
  
}

class CrossStitchMotif implements IMotif {
  /*methods:
   * this.giveDiff()
   * this.giveName()
   * this.startSentence()
   * fields:
   * this.description -- String
   * this.difficulty -- int
   * methods for fields:
   */
  
  String description;
  double difficulty;
  
  CrossStitchMotif (String description, double difficulty){
    this.description = description;
    this.difficulty = difficulty;
  }

  //to find the difficulty of a cross stitch
  public double giveDiff() {
    return difficulty;
  }
  
  //to add the name of a motif
  public String giveName() {
    return ", " + description + " (cross stitch)";
  }

  //to begin a sentence
  public String startSentence() {
   return description + " (cross stitch)";
  }
}
  
class ChainStitchMotif implements IMotif {
  
  /*methods:
   * this.giveDiff()
   * this.giveName()
   * this.startSentence()
   * fields:
   * this.description -- String
   * this.difficulty -- int
   * methods for fields:
   */
  String description;
  double difficulty;
  
  ChainStitchMotif (String description, double difficulty){
    this.description = description;
    this.difficulty = difficulty;
  }

  //to give the difficulty of a chain stitch
  public double giveDiff() {
    return difficulty;
  }
  
  //to give the name of a chain stitch
  public String giveName() {
    return  ", " + description + " (chain stitch)";
  }

  //to start a sentence
  public String startSentence() {
    return description + " (chain stitch)";
  }
}

interface ILoMotif {
 
  /*methods:
   * this.averageDiff()
   * this.sumDiff()
   * this.totalMotifs()
   * this.allNames()
   * this.stitchNames()
   */
  
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
  
  /*methods:
   * this.averageDiff()
   * this.sumDiff()
   * this.totalMotifs()
   * this.allNames()
   * this.stitchNames()
   */
  
  MtLoMotif(){}

  //to find the average difficulty of an empty LoMotif
  public double averageDiff() {
    return 0;
  }

  //to find sum of an empty LoMotif
  public double sumDiff() {
    return 0;
  }

  //to find total motifs in an empty LoMotifs
  public int totalMotifs() {
    return 0;
  }
  
  //to returns a string of names from an empty LoMotif
  public String allNames() {
    return null;
  }

  //to give the stitch names for an empty LoMotif
  public String stitchNames() {
    return ".";
  } 
}

class ConsLoMotif implements ILoMotif {
  
  /*methods:
   * this.averageDiff()
   * this.sumDiff()
   * this.totalMotifs()
   * this.allNames()
   * this.stitchNames()
   * fields:
   * this.first -- IMotif
   * this.rest -- ILoMotif
   * methods for fields:
   * first.giveDiff()
   * rest.sumDiff()
   * rest.totalMotifs()
   * first.startSentence()
   * rest.stitchNames()
   * first.giveName()
   */
  
  String description;
  IMotif first;
  ILoMotif rest;
  
  ConsLoMotif (String description, IMotif first, ILoMotif rest){
    this.description = description;
    this.first = first;
    this.rest = rest;
  }

  //to find the average difficulty of a list of Motifs
  public double averageDiff() {
    return this.sumDiff() / this.totalMotifs();
  }

  //to sum the difficulties of a list of Motifs
  public double sumDiff() {
    return first.giveDiff() + rest.sumDiff();
  }

  //to find the total number of Motifs in a list
  public int totalMotifs() {
    return 1 + rest.totalMotifs();
  }
  
  //returns a string of every Motif name in a list
  public String allNames() {
    return this.description + ": " + first.startSentence() + rest.stitchNames();
  }

  //returns a string of Motif names from a list
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
  
  //tests for averageDiff
  boolean testAverageDiff(Tester t) {
    return t.checkExpect(this.flowers.averageDiff(), 4.316666666666666) &&
           t.checkExpect(this.pillowCover.averageDiff(), 4.09) &&
           t.checkExpect(this.mtPattern.averageDiff(), 0);
    //tests for allNames
    return t.checkExpect(this.flowers.allNames(), 
        "flowers: daisy (cross stitch), poppy (chain stitch), rose (cross stitch).") &&
        t.checkExpect(this.mtPattern.allnames(), null);
    //tests for sumDiff
    return t.checkExpect(this.flowers.sumDiff(), 12.95) &&
        t.checkExpect(this.mtPattern.sumDiff(), 0);
    //tests for totalMotifs
    return t.checkExpect(this.flowers.totalMotifs(), 3) &&
        t.checkExpect(this.mtPattern.totalMotifs(), 0)
    //tests for giveDiff
    return t.checkExpect(this.bird.giveDiff(), 4.5);
    //tests for giveName
    return t.checkExpect(this.bird.giveName(), ", bird (cross stitch)") &&
        t.checkExpect(this.tree.giveName(), ", tree (chain stitch)");
    //tests for startSentence
    return t.checkExpect(this.bird.startSentence(), "bird (cross stitch)") &&
        t.checkExpect(this.tree.startSentence(), "tree (chain stitch)");
    //tests for stitchNames
    return t.checkExpect(this.mtPattern.stitchNames(), ".")
  }
}