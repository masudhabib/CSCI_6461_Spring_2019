package instructions;

import registers.Memory;
import registers.Registers;

public class LDR extends Instructions{
	int r,ix,i,address;
	@Override
	public void execute(String instruction, Registers register, Memory memory) {
		// TODO Auto-generated method stub
		//Sanity check from index 0 to 5
		if(!instruction.substring(0, 6).equals("000001")) {
			System.out.println("Error, not a Load instruction");
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

			/**
			 * Computing the Effective Address:
									Effective Address (EA) = 
			if I field = 0: 
						// NO indirect addressing			
						if  IX = 00, EA = contents of the Address field
						// just indexing
						if IX = 1..3, c(Xj) + contents of the Address field, where j = c(IX)
						// that is, the IX field has an index register number, the contents of which are added to the contents of the address field
			
			if I field  = 1: 
						
						// indirect addressing, but NO indexing
						if IX = 00, c(Address)
						
						// both indirect addressing and indexing
						if IX = 1..3, c(c(Xj) + Address), where j = c(IX)

			 */
			//Specifies one of 32 locations
			address = Integer.parseInt(instruction.substring(11),2);
			
			int effectiveAddress = instructions.ComputingEffectiveAddress.computeEffectiveAddress(i, ix, address, register,memory);
			System.out.println("EA = " + effectiveAddress);
			
			//Move content of temp reg into the MAR. Temp reg now contains EA
			register.setMar(effectiveAddress);
			System.out.println("Move content of temp reg into the MAR. Temp reg now contains EA: " + register.getMar());
			
			//Fetch M[MAR] into MBR
			register.setMbr(memory.getFromMemory(register.getMar()));
			System.out.println("Fetch M[MAR] into MBR: " + register.getMbr());
			
			//Move MAR into temp reg one of the GPRs
			register.setGPRj(r, register.getMbr());
			System.out.println("Move MAR into temp reg one of the GPRs: at register" + r + ", content: " + register.getMbr());
			
			
		}

	}

	@Override
	public void printMessage() {
		// TODO Auto-generated method stub
		System.out.println("LDR instruction, R: " + r + ", IX: " + ix + ", address: " + address + ", I: " + i  );
		
		

	}

}
