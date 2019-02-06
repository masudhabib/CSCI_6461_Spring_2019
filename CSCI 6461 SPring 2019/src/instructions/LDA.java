package instructions;

import registers.Memory;
import registers.Registers;

public class LDA extends Instructions{
	int r,ix,i,address;
	@Override
	public void execute(String instruction, Registers register, Memory memory) {
		// TODO Auto-generated method stub
		//Sanity check from index 0 to 5
				if(!instruction.substring(0, 6).equals("000011")) {
					System.out.println("Error, not a Load Register with address instruction");
				}
				//If it is a load instruction
				//Extract r,x,i, and effective address (EA)
				else {
					
					//r: Register number. From index 6 to 7
					//Specifies one of four general purpose registers; may be referred to by R0 � R3
					r=Integer.parseInt(instruction.substring(6, 8),2);


					//Specifies one of three index registers; may be referred to by X1 � X3. O value indicates no indexing.
					//The value of IX is used to select an index register and to specify indirect addressing:
					//00			No Indexing
					//01			Index Register 1
					//10			Index Register 2
					//11			Index Register 3
					ix = Integer.parseInt(instruction.substring(8,10),2);


					//If I =1, specifies indirect addressing; otherwise, no indirect addressing.
					i=Integer.parseInt(instruction.substring(10, 11),2);
					//Specifies one of 32 locations
					address = Integer.parseInt(instruction.substring(11),2);
					
					//Calculate effective address
					int effectiveAddress = instructions.ComputingEffectiveAddress.computeEffectiveAddress(i, ix, address, register,memory);
					System.out.println("EA = " + effectiveAddress);
					
					//Store effective address into GPR register with its number corresponding to r(0,1,2,3)
					register.setGPRj(r, effectiveAddress);
				
				}
	}

	@Override
	public void printMessage() {
		// TODO Auto-generated method stub
		System.out.println("LDA instruction, R: " + r + ", IX: " + ix + ", address: " + address + ", I: " + i  );

	}

}
