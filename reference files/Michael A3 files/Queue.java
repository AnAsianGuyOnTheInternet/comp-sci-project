/*
Michael Wenzel	
CMIS202
02/26/24
Queue.java
Create basic queue 
*/
public class Queue {
	private QueueRecord head, tail;
	//empty queue constructor
	public Queue(){
			head = null;
			tail = null;
	}
	//add job to 'tail' end of queue
	public void enqueue(Job job) {
		QueueRecord newRecord = new QueueRecord(job);
		if (tail == null) {
			head = newRecord;
			tail = newRecord;
		} else {
			tail.next = newRecord;
			tail = newRecord;
		}
	}
	//remove and return job from 'head' of queue
	public Job dequeue() {
		if (head == null) {
			return null;
		}
		QueueRecord temp = head;
		head = head.next;
		if (head == null) {
			tail = null;
		}
		return temp.job;
	}
	
	private static class QueueRecord {
		public Job job;
		public QueueRecord next;
		public QueueRecord(Job job) {
			this.job = job;
			this.next = null;
		}
	}
}