package devlounge.lambda;

import java.util.List;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import devlounge.lambda.dao.DynamoDBTripDao;
import devlounge.lambda.domain.TripSector;
import devlounge.model.LambdaResult;

public class FindAllTripsHandler implements RequestHandler<Object, LambdaResult<List<TripSector>>> {

	@Override
	public LambdaResult<List<TripSector>> handleRequest(Object input, Context context) {
		
		
		LambdaResult<List<TripSector>> result = new LambdaResult<List<TripSector>>();
		LambdaLogger logger = context.getLogger();
		logger.log("Starting lijo" );
		logger.log("Starting " + this.getClass().getName() + " Lambda\n");

		try {
			List<TripSector> listTripSector = DynamoDBTripDao.instance().findAllTrips(); 
			result.setData(listTripSector);
			logger.log("Found " + listTripSector.size() + " total trips.");
			
			for(TripSector item : listTripSector){
				System.out.println("     " + item.toString());
			}

			result.setSucceeded(true);
			return result;
			
		} catch (Exception e) {
			logger.log("Starting lijo" +e.getMessage() );
			result.setSucceeded(false);
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
