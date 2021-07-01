package devlounge.lambda;

import java.util.List;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import devlounge.lambda.dao.DynamoDBTripDao;
import devlounge.lambda.domain.TripSector;
import devlounge.model.LambdaResult;
import devlounge.model.TripSearchPayload;
import devlounge.model.TripSearchRequest;

public class FindTripsFromCityHandler implements RequestHandler<TripSearchRequest, LambdaResult<List<TripSector>>> {

	@Override
	public LambdaResult<List<TripSector>> handleRequest(TripSearchRequest input, Context context) {
		
		LambdaResult<List<TripSector>> result = new LambdaResult<List<TripSector>>();
		LambdaLogger logger = context.getLogger();
		
		logger.log("Starting " + this.getClass().getName() + " Lambda with input " + input.getCity() + "\n");

		try {
			List<TripSector> listTripSector = DynamoDBTripDao.instance().findTripsFromCity(input.getCity()); 
			result.setData(listTripSector);
			logger.log("Found " + listTripSector.size() + " total trips.\n");
			
			for(TripSector item : listTripSector){
				System.out.println(item.toString());
			}

			result.setSucceeded(true);
			return result;
			
		} catch (Exception e) {
			result.setSucceeded(false);
			result.setErrorCode(1);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
