package com.nali.spreader.util.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.nali.spreader.util.random.NumberRandomer;

public abstract class RandomDataIterator<E> extends DataIterator<E> {

	private Map<Integer, Integer> randomLimits;
	private Map<Integer, Integer> nextOffsets;
	private int maxOffset;
	private int leftSize;
	private int blockOffset;
	private boolean isRandom;

	public RandomDataIterator(int count, int upperCount, int batchSize) {
		super(count, batchSize);
		if (upperCount <= 0) {
			throw new IllegalArgumentException(
					"upperCount must be larger than 0");
		}
		
		if(upperCount >= count) {
			//不随机，全取
			this.leftSize = count;
			this.maxOffset = count - 1;
			this.isRandom = false;
		}else{
			NumberRandomer randomer = new NumberRandomer(0, count);
			List<Integer> numbers = randomer.multiGet(upperCount);
			this.randomLimits = new HashMap<Integer, Integer>();
			this.nextOffsets = new HashMap<Integer, Integer>();
			this.toLimit(numbers);
		}
	}

	private void toLimit(List<Integer> numbers) {
		int lastNumber = 0;
		int lastOffset = 0;
		if (numbers.size() > 0) {
			int size = numbers.size();
			for (int i = 0; i < size; i++) {

				if (i == 0) {
					this.offset = numbers.get(0);
					this.blockOffset = (int)this.offset;
				}

				int number = numbers.get(i);
				// 相差大于1，跳跃数字
				if (number - lastNumber > 1) {
					this.nextOffsets.put(Integer.valueOf(lastOffset), Integer
							.valueOf(number));
					lastOffset = number;
				}

				lastNumber = number;
				this.randomLimits.put(Integer.valueOf(lastOffset), Integer
						.valueOf(number));

				// 最后一个数字
				if (i == size - 1) {
					this.maxOffset = lastOffset;
				}
			}
		}

	}

	@Override
	public boolean hasNext() {
		return this.offset <= this.maxOffset;
	}

	@Override
	public List<E> next() {
		if (hasNext() == false) {
			throw new NoSuchElementException(
					"No element in data iterator, please use hasNext first to check!");
		}
		
		if(isRandom) {
			List<E> list = null;
			
			if(leftSize >= batchSize) {
				list = this.query(offset, batchSize);
				leftSize -= batchSize;
				offset += batchSize;
			}else{
				list = new ArrayList<E>(batchSize);
				if(leftSize > 0) {
					list.addAll(this.query(offset, leftSize));
					leftSize = 0;
					blockOffset = this.nextOffsets.get(this.blockOffset);
					offset = blockOffset;
				}
				
				int leftBatchSize = batchSize - leftSize;
				int intervalSize = 0;
				while(this.hasNext() && intervalSize < leftBatchSize) {
					 int tempSize = this.randomLimits.get(blockOffset);
					 int more = intervalSize + tempSize - leftBatchSize;
					 if(more > 0) {
						 tempSize = leftBatchSize - intervalSize;
					 }
					 
					 long queryEndIndex = offset + tempSize - 1;
					 long realEndIndex = count - 1;
					 long endIndex = Math.min(queryEndIndex, realEndIndex);
					 int limit = (int) (endIndex - offset + 1);
					 
					 List<E> tempList = query(offset, limit);
					 if(more > 0) {
						leftSize = more;
						this.offset += tempList.size(); 
					 }else{
						this.blockOffset = this.nextOffsets.get(this.offset);
						this.offset = this.blockOffset;
					 }
					 
					 intervalSize += tempList.size();
					 list.addAll(tempList);
				}
			}
			return list;
		}else{
			return super.next();
		}
	}
}
