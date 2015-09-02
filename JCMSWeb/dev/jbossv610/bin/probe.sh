#!/bin/sh
### ====================================================================== ###
##                                                                          ##
##  JGroups Cluster Discovery Script                                        ##
##                                                                          ##
### ====================================================================== ###

# Discovers all UDP-based members running on a certain mcast address (use -help for help)
# Probe [-help] [-addr <addr>] [-port <port>] [-ttl <ttl>] [-timeout <timeout>]

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


CLASSPATH=.:$DIRNAME/../lib/commons-logging.jar:$DIRNAME/../server/all/lib/jgroups.jar:$CLASSPATH

# OS specific support (must be 'true' or 'false').
cygwin=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;
esac

if [ $cygwin = "true" ]; then
   CP=`cygpath -wp $CLASSPATH`
else
   CP=$CLASSPATH
fi

java -cp $CP org.jgroups.tests.Probe $*
