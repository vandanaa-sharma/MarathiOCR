# MarathiOCR

Optical character recognition for **marathi** language.
A simple implementation for **OCR** in Marathi using tess-two library.

![Screenshots](https://drive.google.com/uc?export=download&id=0BzzRMu6z0xt0aHJjQ0dPa29DNlE)


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

  While trying to build **eyes-two** (tess-two) project -   
- Space not acceptable in ndk path
- Error : System cannot find the specified path (for ndk) -
  Fixed by downloading correct ndk from - https://developer.android.com/ndk/downloads/index.html 
- Error - Your build script points to an unknown file jni/Android.mk
 Fixed by creating new environment variable NDK_PROJECT_PATH and setting it's value to - F:\ocrProject\tess-two\tess-two
 
 2. While setting project path (running command - android update project --path ocrProject\tess-two)
- Error1 - Android is not recognized as an internal or external command. 
  **Fix** - Add the path to android.bat file to your PATH variable and re-launch command prompt
  Path to android.bat - E:\AndriodSDKS\tools
- Error2 - The project either has no target set or the target is invalid. 
  **Fix** - add --target argument
   *android update project --path F:\ocrProject\tess-two\tess-two --target android-23*
   You can check the list of avaiable targets in our SDKs by running command - android list targets
   
 3.  While running command - **ant release** :
    - Apache ant was not installed on machine, downloaded apache ant and added the following path to PATH environment variable - F:\apache-ant-1.9.7\bin
    - Created a variable **JAVA_HOME** in environment variables and set its value to - C:\Program Files\Java\jdk1.8.0_111
    - Re-launched command prompt in tess-two
 
 4.  While setting up Android Studio 
    - Correct project settings- Go to File >> Project Structure >> Make sure the path to our sdk, jdk and ndk is the latest one you installed
    - In gradle.properties file change the value of org.gradle.jvmargs=-Xmx512m  (Windows 7 32 bit space allocation related error)

 5. On adding libraries/tess-two to android project -
   - Caution: Copy all the contents of your internal tess-two folder into libraries/tess-two in android project tree (create new directory libraries)
   - Error1 - Plugin with id 'com.jfrog.bintray' not found 
   - Error2 - Plugin with id 'com.github.dcendents.android-maven'
   **Fix** - Removed lines - 
      *apply plugin: 'com.github.dcendents.android-maven', 
      apply plugin: 'com.jfrog.bintray' from tess-two’s build.gradle file*
    - And added following dependencies in main build.gradle - 
       *classpath 'com.github.dcendents:android-maven-plugin:1.2', 
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.2'*
   - Error3 -  Could not find definitions for tess-two’s build.gradle file’s install{} and bintray{} blocks. 
     **Fix** - Commented the blocks temporarily
   
 6. Error - datapath does not exist on device at - mTess.init(datapath, languag) in OCRFunctions.java (mTess is TessBaseAPI’s object). 
     **Fix** - Added mar.trained data file to the folder “storage/emulated/0/tesseract/tessdata/” (folders created manually)
