...
Guint numSyms = 0; // - размер всех словарей

  for () {
    numSyms += ((JBIG2SymbolDict *)seg)->getSize();  // - получение общего размера всех словарей
  }
...
  syms = (JBIG2Bitmap **)gmallocn(numSyms, sizeof(JBIG2Bitmap *)); // - выделение буфера для JBIG2


  kk = 0;

  for () {
    syms[kk++] = symbolDict->getBitmap(k); // - запись Bitmap в выделенный буфер
  }