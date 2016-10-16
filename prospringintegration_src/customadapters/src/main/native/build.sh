export JDK_INCLUDE_DIR="$JAVA_HOME/include";
touch libsifsmon.so; rm libsifsmon.so;
gcc  -o libsifsmon.so -shared  -fPIC -I$JDK_INCLUDE_DIR -I$JDK_INCLUDE_DIR/linux fsmon.c -lc ;
