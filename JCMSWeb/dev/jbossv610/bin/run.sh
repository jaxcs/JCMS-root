#!/bin/sh
### ====================================================================== ###
##                                                                          ##
##  JBoss Bootstrap Script                                                  ##
##                                                                          ##
### ====================================================================== ###

### $Id: run.sh 111395 2011-05-18 07:45:07Z beve $ ###

# Extract the directory and the program name
# takes care of symlinks
PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG="`dirname "$PRG"`/$link"
  fi
done
DIRNAME=`dirname "$PRG"`
PROGNAME=`basename "$PRG"`

GREP="grep"

# Use the maximum available, or set MAX_FD != -1 to use that
MAX_FD="maximum"

#
# Helper to complain.
#
warn() {
    echo "${PROGNAME}: $*"
}

#
# Helper to puke.
#
die() {
    warn $*
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false;
darwin=false;
linux=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;

    Darwin*)
        darwin=true
        ;;

    Linux)
        linux=true
        ;;
esac

# Read an optional running configuration file
if [ "x$RUN_CONF" = "x" ]; then
    RUN_CONF="$DIRNAME/run.conf"
fi
if [ -r "$RUN_CONF" ]; then
    . "$RUN_CONF"
fi

# Force IPv4 on Linux systems since IPv6 doesn't work correctly with jdk5 and lower
if [ "$linux" = "true" ]; then
   JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
    [ -n "$JBOSS_HOME" ] &&
        JBOSS_HOME=`cygpath --unix "$JBOSS_HOME"`
    [ -n "$JAVA_HOME" ] &&
        JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
    [ -n "$JAVAC_JAR" ] &&
        JAVAC_JAR=`cygpath --unix "$JAVAC_JAR"`
fi

# Setup JBOSS_HOME
if [ "x$JBOSS_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    JBOSS_HOME=`cd $DIRNAME/..; pwd`
fi
export JBOSS_HOME

# Increase the maximum file descriptors if we can
if [ "$cygwin" = "false" ]; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ "$?" -eq 0 ]; then
        # Darwin does not allow RLIMIT_INFINITY on file soft limit
        if [ "$darwin" = "true" -a "$MAX_FD_LIMIT" = "unlimited" ]; then
            MAX_FD_LIMIT=`/usr/sbin/sysctl -n kern.maxfilesperproc`
        fi

    if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ]; then
        # use the system max
        MAX_FD="$MAX_FD_LIMIT"
    fi

    ulimit -n $MAX_FD
    if [ "$?" -ne 0 ]; then
        warn "Could not set maximum file descriptor limit: $MAX_FD"
    fi
    else
        warn "Could not query system maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

# Setup the classpath
JBOSS_BOOT_CLASSPATH="$JBOSS_HOME/bin/run.jar"
if [ ! -f "$JBOSS_BOOT_CLASSPATH" ]; then
    die "Missing required file: $JBOSS_BOOT_CLASSPATH"
fi

# Tomcat uses the JDT Compiler
# Only include tools.jar if someone wants to use the JDK instead.
# compatible distribution which JAVA_HOME points to
JAVAC_JAR_FILE="${JAVAC_JAR:-$JAVA_HOME/lib/tools.jar}"
if [ ! -f "$JAVAC_JAR_FILE" ]; then
   # MacOSX does not have a seperate tools.jar
   if [ "$darwin" != "true" -a "x$JAVAC_JAR" != "x" ]; then
      warn "Missing file: JAVAC_JAR=$JAVAC_JAR"
      warn "Unexpected results may occur."
   fi
   JAVAC_JAR_FILE=
fi

# Setup classpath 
JBOSS_CLASSPATH="${JBOSS_CLASSPATH:+$JBOSS_CLASSPATH:$JBOSS_BOOT_CLASSPATH}"
JBOSS_CLASSPATH="${JBOSS_CLASSPATH:-$JBOSS_BOOT_CLASSPATH}"
JBOSS_CLASSPATH="$JBOSS_CLASSPATH${JAVAC_JAR_FILE:+:$JAVAC_JAR_FILE}"

# Check for -d32/-d64 in JAVA_OPTS
JVM_OPTVERSION="-version"
JVM_D64_OPTION=`echo $JAVA_OPTS | $GREP "\-d64"`
JVM_D32_OPTION=`echo $JAVA_OPTS | $GREP "\-d32"`
test "x$JVM_D64_OPTION" != "x" && JVM_OPTVERSION="-d64 $JVM_OPTVERSION"
test "x$JVM_D32_OPTION" != "x" && JVM_OPTVERSION="-d32 $JVM_OPTVERSION"

# If -server not set in JAVA_OPTS, set it, if supported
SERVER_SET=`echo $JAVA_OPTS | $GREP "\-server"`
if [ "x$SERVER_SET" = "x" ]; then

    # Check for SUN(tm) JVM w/ HotSpot support
    if [ "x$HAS_HOTSPOT" = "x" ]; then
        HAS_HOTSPOT=`"$JAVA" $JVM_OPTVERSION -version 2>&1 | $GREP -i HotSpot`
    fi

    # Check for OpenJDK JVM w/server support
    if [ "x$HAS_OPENJDK_" = "x" ]; then
        HAS_OPENJDK=`"$JAVA" $JVM_OPTVERSION 2>&1 | $GREP -i OpenJDK`
    fi

    # Enable -server if we have Hotspot or OpenJDK, unless we can't
    if [ "x$HAS_HOTSPOT" != "x" -o "x$HAS_OPENJDK" != "x" ]; then
        # MacOS does not support -server flag
        if [ "$darwin" != "true" ]; then
            JAVA_OPTS="-server $JAVA_OPTS"
            JVM_OPTVERSION="-server $JVM_OPTVERSION"
        fi
    fi
else
    JVM_OPTVERSION="-server $JVM_OPTVERSION"
fi

# Setup JBoss specific properties
JAVA_OPTS="${JAVA_OPTS:+$JAVA_OPTS -Dprogram.name=$PROGNAME}"
JAVA_OPTS="${JAVA_OPTS:--Dprogram.name=$PROGNAME}"
JAVA_OPTS="${JAVA_OPTS:+$JAVA_OPTS -Dlogging.configuration=file:${DIRNAME}/logging.properties}"

# Setup JBoss Native library path
#
if [ -d "$JBOSS_HOME/../native/lib" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/../native" && pwd`
elif [ -d "$JBOSS_HOME/native/lib" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/native" && pwd`
elif [ -d "$JBOSS_HOME/../native/lib64" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/../native" && pwd`
elif [ -d "$JBOSS_HOME/native/lib64" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/native" && pwd`
elif [ -d "$JBOSS_HOME/native/bin" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/native" && pwd`
elif [ -d "$JBOSS_HOME/bin/native" ]; then
    JBOSS_NATIVE_DIR=`cd "$JBOSS_HOME/bin/native" && pwd`
fi
if [ -d "$JBOSS_NATIVE_DIR" ]; then
    if $cygwin; then
        JBOSS_NATIVE_DIR="$JBOSS_NATIVE_DIR/bin"
        export PATH="$JBOSS_NATIVE_DIR:$PATH"
        JBOSS_NATIVE_LIBPATH=`cygpath --path --windows "$JBOSS_NATIVE_DIR"`
    else
        IS_64_BIT_JVM=`"$JAVA" $JVM_OPTVERSION 2>&1 | $GREP -i 64-bit`
        if [ "x$IS_64_BIT_JVM" != "x" ]; then
            JBOSS_NATIVE_DIR="$JBOSS_NATIVE_DIR/lib64"
        else
            JBOSS_NATIVE_DIR="$JBOSS_NATIVE_DIR/lib"
        fi
        LD_LIBRARY_PATH="$JBOSS_NATIVE_DIR${LD_LIBRARY_PATH:+:$LD_LIBRARY_PATH}"
        export LD_LIBRARY_PATH
        JBOSS_NATIVE_LIBPATH=$LD_LIBRARY_PATH
    fi
    JAVA_OPTS="$JAVA_OPTS -Djava.library.path=$JBOSS_NATIVE_LIBPATH"
fi

# Setup the java endorsed dirs
JBOSS_ENDORSED_DIRS="$JBOSS_HOME/lib/endorsed"

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
    JBOSS_HOME=`cygpath --path --windows "$JBOSS_HOME"`
    JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
    JBOSS_CLASSPATH=`cygpath --path --windows "$JBOSS_CLASSPATH"`
    JBOSS_ENDORSED_DIRS=`cygpath --path --windows "$JBOSS_ENDORSED_DIRS"`
fi

# Display our environment
echo "========================================================================="
echo ""
echo "  JBoss Bootstrap Environment"
echo ""
echo "  JBOSS_HOME: $JBOSS_HOME"
echo ""
echo "  JAVA: $JAVA"
echo ""
echo "  JAVA_OPTS: $JAVA_OPTS"
echo ""
echo "  CLASSPATH: $JBOSS_CLASSPATH"
echo ""
echo "========================================================================="
echo ""

while true; do
   if [ "x$LAUNCH_JBOSS_IN_BACKGROUND" = "x" ]; then
      # Execute the JVM in the foreground
      eval \"$JAVA\" $JAVA_OPTS \
         -Djava.endorsed.dirs=\"$JBOSS_ENDORSED_DIRS\" \
         -classpath \"$JBOSS_CLASSPATH\" \
         org.jboss.Main "$@"
      JBOSS_STATUS=$?
   else
      # Execute the JVM in the background
      eval \"$JAVA\" $JAVA_OPTS \
         -Djava.endorsed.dirs=\"$JBOSS_ENDORSED_DIRS\" \
         -classpath \"$JBOSS_CLASSPATH\" \
         org.jboss.Main "$@" "&"
      JBOSS_PID=$!
      # Trap common signals and relay them to the jboss process
      trap "kill -HUP  $JBOSS_PID" HUP
      trap "kill -TERM $JBOSS_PID" INT
      trap "kill -QUIT $JBOSS_PID" QUIT
      trap "kill -PIPE $JBOSS_PID" PIPE
      trap "kill -TERM $JBOSS_PID" TERM
      if [ "x$JBOSS_PIDFILE" != "x" ]; then
        echo $JBOSS_PID > $JBOSS_PIDFILE
      fi
      # Wait until the background process exits
      WAIT_STATUS=128
      while [ "$WAIT_STATUS" -ge 128 ]; do
         wait $JBOSS_PID 2>/dev/null
         WAIT_STATUS=$?
         if [ "$WAIT_STATUS" -gt 128 ]; then
            SIGNAL=`expr $WAIT_STATUS - 128`
            SIGNAL_NAME=`kill -l $SIGNAL`
            echo "*** JBossAS process ($JBOSS_PID) received $SIGNAL_NAME signal ***" >&2
         fi
      done
      if [ "$WAIT_STATUS" -lt 127 ]; then
         JBOSS_STATUS=$WAIT_STATUS
      else
         JBOSS_STATUS=0
      fi
      if [ "$JBOSS_STATUS" -ne 10 ]; then
            # Wait for a complete shudown
            wait $JBOSS_PID 2>/dev/null
      fi
   fi
   # If restart doesn't work, check you are running JBossAS 4.0.4+
   #    http://jira.jboss.com/jira/browse/JBAS-2483
   # or the following if you're running Red Hat 7.0
   #    http://developer.java.sun.com/developer/bugParade/bugs/4465334.html
   if [ "$JBOSS_STATUS" -eq 10 ]; then
      echo "Restarting JBoss..."
   else
      exit $JBOSS_STATUS
   fi
done

