    echo "Installing drivers"
    touch /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0bb4", MODE="0666"' > /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0e79", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0502", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0b05", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="413c", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0489", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="091e", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="18d1", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0bb4", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="12d1", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="24e3", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="2116", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0482", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="17ef", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="1004", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="22b8", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0409", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="2080", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0955", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="2257", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="10a9", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="1d4d", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0471", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="04da", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="05c6", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="1f53", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="04e8", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="04dd", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0fce", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="0930", MODE="0666"' >> /tmp/android.rules
    echo -e 'SUBSYSTEM=="usb", ATTRS{idVendor}=="19d2", MODE="0666"' >> /tmp/android.rules
    sudo cp /tmp/android.rules /etc/udev/rules.d/51-android.rules
    sudo chmod 644 /etc/udev/rules.d/51-android.rules
    sudo chown root. /etc/udev/rules.d/51-android.rules
    mkdir ~/android &> /dev/null && touch ~/android/adb_usb.ini &> /dev/null
    echo -e '0x2717' >> ~/android/adb_usb.ini
    sudo service udev restart
    sudo killall adb
    echo "Done!"