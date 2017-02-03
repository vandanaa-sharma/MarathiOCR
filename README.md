# MarathiOCR

Optical character recognition for **marathi** language.
A simple implementation for **OCR** in Marathi using tess-two library.

----------

Downloads
-------------
1.  **Tess Two Project** - https://github.com/rmtheis/tess-two/
2.  **Trained data files** - https://github.com/tesseract-ocr/tessdata
**Marathi trained data** - https://github.com/tesseract-ocr/tessdata/blob/master/mar.traineddata
**English trained data** - https://github.com/tesseract-ocr/tessdata/blob/master/eng.traineddata
3.  **Apache Ant (1.9.7)** - http://mirror.fibergrid.in/apache//ant/binaries/apache-ant-1.9.7-bin.zip 
4.  **Android NDK** (r13-b Windows 32-bit) - http://dl.google.com/android/ndk/android-ndk-r10c-windows-x86_64.exe
5. **Android Studio 2.2** (requires Java 1.8 or later) Windows 32 -bit) - https://developer.android.com/studio/index.html?gclid=Cj0KEQjwhvbABRDOp4rahNjh-tMBEiQA0QgTGmlD8svYooPgNCNkPyGEN_j5CcCtd_3U_klg5WvDfVcaAv-Y8P8HAQ 
6. **JDK 1.8**- http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html



> **Note:**

> - Add path for NDK, SDK, JDK and Apache ANT to the PATH environment variable 

#### <i class="icon-link"></i> Followed Set- up Instruction from -
 https://www.codeproject.com/tips/840623/android-character-recognition


----------
Errors encountered
-------------------

 1.   **NDK build errors**: (running command ndk-build in ocrProject\tess-two\)

	> - While building **eyes-two** (tess-two) project, note that <space>  is not acceptable in ndk path
 - Error  - System cannot find the specified path (for ndk)
   Fixed by downloading **correct** ndk from - https://developer.android.com/ndk/downloads/index.html 
 - Error - Your build script points to an unknown file jni/Android.mk
  Fixed by creating new environment variable NDK_PROJECT_PATH and setting it's value to - F:\ocrProject\tess-two\tess-two
