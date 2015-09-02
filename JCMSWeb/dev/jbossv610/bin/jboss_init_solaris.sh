#!/usr/bin/bash
#
# $Id: jboss_init_solaris.sh 57106 2006-09-23 14:09:54Z dimitris@jboss.org $
#
# JBoss Control Script for Solaris
#
# description: JBoss Applicaton Server / EJB Container
#
# Run this script as the root user.  This script will start
# as many instances of JBoss as there are array entries in a
# startup configuration file under /etc/rc.config.d/ as
# defined below.  If no configuration file exists, the script
# will still attempt to start at least one server using default
# values.
#
# Installation Instructions:
# 1. Copy <jboss-home>/bin/jboss_init_solaris.sh to/sbin/init.d/jboss
# 2. Create softlinks to the file from the various startup and shutdown 
#    directories:
#    ln -s /etc/init.d/jboss /etc/rc2.d/K01jboss
#    ln -s /etc/init.d/jboss /etc/rc3.d/S99jboss
# 3. Create /etc/default/jboss with the variables defined below. For 
#    example if you have two servers at /opt/jboss1 and /opt/jboss2 that run 
#    as users jboss1 and jboss2 using the default instances, then you would 
#    create the following configuration entries:
#############
#    JBOSS_HOME[0]=/opt/jboss1
#    JBOSS_USER[0]=jboss1
#    SERVER_NAME[0]=default
#    JBOSS_START[0]=1
##   default console logging (you can also just omit the next line)
#    JBOSS_CONSOLE[0]=""
#############
#    JBOSS_HOME[1]=/opt/jboss2
#    JBOSS_USER[1]=jboss2
#    SERVER_NAME[1]=default
#    JBOSS_START[1]=1
##   No Console logging
#    JBOSS_CONSOLE[1]=/dev/null
#

##################################################################
#
# This script will start as many instances of JBoss as there are 
# consecutive array entries for the variables defined below.
#
# There may be a unique jboss server instance for each $JBOSS_HOME[i] 
# and $SERVER_NAME[i] combination.
#
# The following variables should be defined in /etc/default/jboss.
# If they are not, the variables are defaulted below:
#
# JBOSS_HOME[i] - root directory for jboss installation, like 
#   /opt/jboss-<version>. 
#   The default for the 1st instance is /opt/jboss: JBOSS_HOME[0]=/opt/jboss.
#   No default for any other instance.
#
# SERVER_NAME[i] - server instance name.  Normally "all", "default", or
#   "minimal". Maps to server instance at $JBOSS_HOME/server/$SERVER_NAME
#   The default value is "default".
#
# JBOSS_START[i] - set to "0" to force JBOSS to not start when this script
#   is run. Usefull when this script is run automatically at system
#   startup time. Default is "1" to startup.
#
# JBOSS_USER[i] - it is username by which the jboss application server
#   is started. The default user is jboss.
#
# JBOSS_CONSOLE[i] - file where jboss console logging will be written
#   Use "/dev/null" if console logging is not desired.
#   default is /var/opt/jboss/jboss_<user>_<instance>.log
#
# JAVA_HOME should also be defined in the target users environment, such
#   as is the case when it is exported from ~/.profile.
#
# NOTE: The notation for array subscript [0] is optional.  You can refer to 
# JBOSS_HOME[0] as JBOSS_HOME. 
#
if [ -f /etc/default/jboss ]
then
  . /etc/default/jboss
fi

umask 022

#
# Make sure variables are defined at least for the first instance of the
# array.  Note that $SERVER_NAME is the same as ${SERVER_NAME[0]}

JBOSS_HOME[0]=${JBOSS_HOME:-"/opt/jboss"}
JBOSS_USER[0]=${JBOSS_USER:-"jboss"}
SERVER_NAME[0]=${SERVER_NAME:-"default"}


PATH=/sbin:/usr/sbin:/usr/bin:$PATH
export PATH

#Usage: isJBossRunning <jbossHome> <jbossUserId> <instance>
# sets non zero return code if Jboss is not running
# sets global isJBossRunningPid with 1st process id if it is running 
isJBossRunning()
{
    JBossUserId=$1
    JBossHome=$2
    JBossInstance=$3
    # Note that we don't check $instance, as it will be truncated in
    # the -o args output below

    # Find correct run.sh process (we might find more than 1 candidate)
    isJBossRunningPid=`ps -u ${JBossUserId} -o pid -o args | \
      awk '{if (index($0, "'${JBossHome}'") && $2 ~ /sh$/) print $1}'`
    if [ "x${isJBossRunningPid}" = "x" ]; then
      return 1
    fi

    # Find java process with one of the above processes as parent
    for JBossPid in ${isJBossRunningPid}; do
      isPidRunning=`ps -u ${JBossUserId} -o pid -o args -o ppid | \
        awk '{if ($2 ~ /java$/ && $NF ~ /^'${JBossPid}'$/) print $1}'`
      if [ "x${isPidRunning}" != "x" ]; then
        args=`pargs -l ${JBossPid}`
        test "x${args}" = "x" && return 0
        inst=`echo ${args} | grep "\-c ${JBossInstance}" 2>/dev/null`
        test "x${inst}" = "x" && return 0
      fi
    done
    return 1
}

#USAGE: isUserOK
# prints any problems on standard error and returns with a non-zero status
# returns a 0 status if all is OK
isUserOK()
{
    # check if the user exists
    id ${JBOSS_USER[i]} >/dev/null 2>&1
    if (($?!=0)); then
	echo "ERROR: The user ${JBOSS_USER[i]} does not exist." 1>&2
	return 1
    fi

    # check if the user has write permission on the jboss instance directory
    su ${JBOSS_USER[i]} -c "test -w ${JBOSS_HOME[i]}/server/${SERVER_NAME[i]}" >/dev/null 2>&1
    if (($?!=0)); then
	echo "ERROR: The user ${JBOSS_USER[i]} does not have write access to ${JBOSS_HOME[i]}/server/${SERVER_NAME[i]}" 1>&2
	return 2
    fi

    # user must have JAVA_HOME defined
    su - ${JBOSS_USER[i]} -c 'whence java || [[ -d $JAVA_HOME ]]' >/dev/null 2>&1
    if (($?!=0)); then
	echo "ERROR: The user \"${JBOSS_USER[i]}\" does not have \$JAVA_HOME defined; either define \$JAVA_HOME for this user or ensure that a path to \"java\" exists." 1>&2
	return 3
    fi

    # user looks good so far
    return 0
}

# Usage: startJBoss <index>
startJBoss()
{
    typeset -i i=$1

    (( ${JBOSS_START[i]} != 1 )) && return 0

    # make sure the console log exists with appropriate permissions for JBOSS_USER[i]
    if [[ ! -d ${JBOSS_CONSOLE[i]%/*} ]]; then
	mkdir -p ${JBOSS_CONSOLE[i]%/*}
	chmod 0755 ${JBOSS_CONSOLE[i]%/*} 
	chown ${JBOSS_USER[i]} ${JBOSS_CONSOLE[i]%/*}
    fi

    # do some basic error checking
    if [[ ! -d ${JBOSS_HOME[i]} ]]; then
	echo "ERROR: JBOSS_HOME[$i] is not a valid directory : ${JBOSS_HOME[i]}" 1>&2
	return 1
    fi

    isUserOK || return 2

    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]}
    if (( $? == 0 )); then
	echo "JBoss AS is already running for user ${JBOSS_USER[i]} at ${JBOSS_HOME[i]} with instance ${SERVER_NAME[i]}" 1>&2
	return 3
    fi

    # keep last version of the console log around
    [[ -f ${JBOSS_CONSOLE[i]} ]] && mv ${JBOSS_CONSOLE[i]} ${JBOSS_CONSOLE[i]}.old

    # JBoss is not running, start it up
    CMD_START="${JBOSS_HOME[i]}/bin/run.sh -c ${SERVER_NAME[i]}"
    nohup su - ${JBOSS_USER[i]} -c "sh $CMD_START" >${JBOSS_CONSOLE[i]} 2>&1 &
    echo "Starting JBoss AS for user ${JBOSS_USER[i]} at ${JBOSS_HOME[i]} with instance ${SERVER_NAME[i]}."
    
    # wait a few seconds then check if it started ok
    #isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} || sleep 2 ||
    #isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} || sleep 3 ||
    #echo "... server not started yet. Check the log files for errors"
}

# Usage: stopJBoss <index>
stopJBoss()
{
    typeset -i i=$1

    # return silently if JBoss AS is not running
    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} || return 0

    # JBoss is running and $isJBossRunningPid is set to the process id
    #  SIGTERM does a gracefull shutdown like ^C
    echo "Stopping JBoss AS PID $isJBossRunningPid for user ${JBOSS_USER[i]} at ${JBOSS_HOME[i]} with instance ${SERVER_NAME[i]}"
    kill -s SIGTERM $isJBossRunningPid

    # wait for up to 30 seconds for the process to terminate gracefully
    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} && printf "please wait " && sleep 5 &&
    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} && printf "." && sleep 10 &&
    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} && printf "." && sleep 15 &&
    isJBossRunning ${JBOSS_USER[i]} ${JBOSS_HOME[i]} ${SERVER_NAME[i]} && 
    echo "NOT Terminated! Wait a moment then check to see if the process has shut down gracefully, or terminate it now with\n  \"kill -s SIGKILL $isJBossRunningPid\"" >&2
}

# Usage: doAll <command> <command args>
doAll()
{
    typeset cmd=$1
    typeset -i rc=0
    shift

    # loop thru the configuration file for servers to start or stop as identified by JBOSS_HOME[i] and the
    # other parameters

    ((i=0))
    while [[ ${JBOSS_HOME[i]} != "" ]]
    do

      # set defaults for any missing values
      SERVER_NAME[$i]=${SERVER_NAME[i]:-"default"}
      JBOSS_USER[$i]=${JBOSS_USER[i]:-"jboss"} 
      JBOSS_CONSOLE[$i]=${JBOSS_CONSOLE[i]:-/var/opt/jboss/${JBOSS_USER[i]}/jboss_${SERVER_NAME[i]}.log}
      JBOSS_START[$i]=${JBOSS_START[i]:-1}

      $cmd $i $*
      (($? != 0)) && rc=$?
      ((i=i+1))
    done
    return $rc
}

case "$1" in
    start_msg)
	echo "Starting JBoss"
	;;
    start)
	doAll startJBoss
	;;
    stop_msg)
	echo "Stopping JBoss"
	;;
    stop)
	doAll stopJBoss
	;;
    restart)
	$0 stop
	$0 start
	;;
    *)
	echo "usage: $0 (start|stop|restart|help)" 1>&2
	exit 1
esac

exit $?
