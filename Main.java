class Main {
  public static void main(String[] args) {
    (new Main()).init();
  }

  void print(Object o) {
    System.out.println(o);
  }

  void printt(Object o) {
    System.out.print(o);
  }

  void init() {
    String original = Input.readFile("original.txt");
    Input.writeFile("encrypted.txt", encodeMessage(original));
    Input.writeFile("decrypted.txt", decodeMessage(Input.readFile("encrypted.txt")));

  }


  String encodeMessage(String msg) {
    char[] find = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K', 'L', 'M', 'N', 'O', 'P', 'Q','R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'};

    // temporarily commented out, to be continued
    // 'g', 'h', 'i', 'j', 'k', 'l',
    // 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    // 'u', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    // 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
    // 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'

    char[] replace = { '↝', '↭', '↕', '∔', '∳', '⌧', 'ᚌ','Ꭶ','Ꭷ','Ꭸ','Ꮉ','Ꮿ','Ᏹ','Ᏼ','Ꮽ','ᐂ','ᑒ','ᒌ','ᔜ','ᕯ','ᘠ','ᛝ','	ឃ','ឆ','ឧ','៊','២','ᠠ','ᢇ','ᾕ','ῗ','ῼ','‡','⁂','₯','℄','℟','⇥','⇝','∋','∳','≉','≬','	≰','≴','⊥','⊦','⊷','⌇','⌘'};


    String layer1 = subEncryption(msg, find, replace);
    Input.writeFile("encrypted1.txt", layer1);

    String layer2 = combCipher(layer1, 3, -5);
    Input.writeFile("encrypted2.txt", layer2);

    String layer3 = noiseAdder(layer2, 1);
    Input.writeFile("encrypted3.txt", layer3);

    return layer3;
  }

  String decodeMessage(String msg) {
    char[] find = { 'a', 'b', 'c', 'd', 'e', 'f', };
    char[] replace = { '↝', '↭', '↕', '∔', '∳', '⌧', };

    String layer3 = noiseRemover(msg, 1);

    String layer2 = combCipher(layer3, -3, 5);

    String layer1 = subEncryption(layer2, replace, find);

    return layer1;

  }

  // reverse string
  String reverse(String txt) {
    String bld = "";
    for (int x = 0; x <= txt.length() - 1; x++) {
      bld = txt.charAt(x) + bld;
    }
    return bld;
  }

  // comb cipher
  String combCipher(String msg, int s1, int s2) {
    String build = "";
    for (int i = 0; i < msg.length(); i++) {
      int ascii = (int) msg.charAt(i);
      ascii = (i % 2 == 0) ? ascii + s1 : ascii + s2;
      build += (char) ascii;
    }
    return build;
  }

  String noiseAdder(String msg, double interval) {
    String build = "";
    for (int i = 0; i < msg.length(); i++) {
      if (i % interval == 0) {
        build += msg.substring(i, i + 1) + (char) randInt(32, 126);
      } else {
        build += msg.charAt(i);
      }
    }
    return build;
  }

  String noiseRemover(String msg, double interval) {
    String build = "";
    int count = 0;
    for (int i = 0; i < msg.length(); i++) {
      build += msg.charAt(i);
      if (count % interval == 0) {
        i++;
      }
      count++;
    }
    return build;
  }

  // Cipher encoding with no wrapping
  String caesarCipher(String txt, int shift) {
    String bld = "";
    int ascii;
    char ch = '\0';
    for (int x = 0; x <= txt.length() - 1; x++) {
      ch = txt.charAt(x);
      ascii = (int) ch;
      ascii += shift;
      bld += (char) ascii;
    }

    return bld;
  }

  // String decode(String txt){
  // String bld="";
  // int ascii;
  // char ch='\0';
  // for(int x=0; x<=txt.length()-1;x++){
  // ch=txt.charAt(x);
  // ascii=(int)ch;
  // ascii-=1;
  // bld+= (char)ascii;
  // }
  // return bld;
  // }

  // Substituion encoding
  String subEncryption(String s, char[] sub, char[] sub2) {
    String bld = "";
    char ch = '\0';
    int index = 0;
    for (int x = 0; x <= s.length() - 1; x++) {
      ch = s.charAt(x);
      index = indexOf(ch, sub);
      if (index != -1) {
        bld += sub2[index];
      } else {
        bld += ch;
      }
    }
    return bld;
  }

  int indexOf(char ch, char[] arry) {
    for (int x = 0; x <= arry.length - 1; x++) {
      if (arry[x] == ch) {
        return x;
      }
    }
    return -1;
  }

  int randInt(int lower, int upper) {
    int range = upper - lower;
    return (int) (Math.random() * range + lower);
  }

 String lettersBehind(String txt){
    String bld = "";
    for(int x = 0; x <= txt.length() - 1; x++){
      if(x == 0){
        bld += txt.substring(x,x+1);
      }
      else if(x == 1){
        bld += txt.substring(x,x+1) +txt.substring(x-1,x);
      }
      else{
        bld += txt.substring(x,x+1)+txt.substring(x-1,x) + txt.substring(x - 2,x-1);
      }
      
    }
    System.out.println(bld);
    return bld;
  }





}
