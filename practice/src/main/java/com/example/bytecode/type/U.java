package com.example.bytecode.type;

public abstract class U {

    static final char[] hexChar = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'
    };

    private byte[] value;

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public abstract Integer toInt();

    public String toHexString(){

        StringBuilder hxStr = new StringBuilder();
        for (int i = 1; i > 0; i--){
            int v = value[i] & 0xff;

            while (v > 0){
                int c = v % 16;
                v = v >>> 4;
                hxStr.insert(0, hexChar[c]);
            }
            if ((hxStr.length() & 0x01) == 1){
                hxStr.insert(0, '0');
            }
        }
        return "0x" + (hxStr.length() == 0 ? "00" : hxStr.toString());
    }
}
