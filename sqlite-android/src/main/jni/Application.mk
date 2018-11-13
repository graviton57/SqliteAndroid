# Version X: armeabi
#APP_PLATFORM=android-8
#APP_ABI := armeabi

# Version X+1: armeabi-v7a (Much faster due to the availability of hardware
# FP, but cannot be run in the emulator).
#APP_PLATFORM=android-8
#APP_ABI := armeabi-v7a

# Version X+2: x86 (Requires android-9, so a change needs to be made in
# AndroidManifest.xml too)
#APP_PLATFORM=android-9
#APP_ABI := x86


APP_ABI := armeabi armeabi-v7a x86 arm64-v8a
#APP_ABI := armeabi-v7a,arm64-v8a,x86,x86_64
#APP_STL:=gnustl_static
APP_STL := c++_shared
#APP_STL:=stlport_static

APP_MODULES := sqlitefunctions sqliteX
