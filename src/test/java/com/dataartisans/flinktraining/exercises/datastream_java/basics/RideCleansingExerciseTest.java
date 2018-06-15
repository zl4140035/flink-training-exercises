package com.dataartisans.flinktraining.exercises.datastream_java.basics;

import com.dataartisans.flinktraining.exercises.datastream_java.datatypes.TaxiRide;
import com.dataartisans.flinktraining.exercises.datastream_java.testing.TaxiRideTestBase;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RideCleansingExerciseTest extends TaxiRideTestBase<TaxiRide> {
	TestSink<TaxiRide> sink = new TestSink<TaxiRide>();

	static Testable javaExercise = () -> RideCleansingExercise.main(new String[]{});
	static Testable javaSolution = () -> com.dataartisans.flinktraining.solutions.datastream_java.RideCleansingSolution.main(new String[]{});

	static Testable scalaExercise = () -> com.dataartisans.flinktraining.exercises.datastream_scala.basics.RideCleansingExercise.main(new String[]{});
	static Testable scalaSolution = () -> com.dataartisans.flinktraining.solutions.datastream_scala.RideCleansingSolution.main(new String[]{});

	public TestSink<TaxiRide> javaResults(TestSource source) throws Exception {
		TestSink<TaxiRide> sink = new TestSink<TaxiRide>();
		runTest(source, sink, javaExercise, javaSolution);
		return sink;
	}

	public TestSink<TaxiRide> scalaResults(TestSource source) throws Exception {
		TestSink<TaxiRide> sink = new TestSink<TaxiRide>();
		runTest(source, sink, scalaExercise, scalaSolution);
		return sink;
	}

	@Test
	public void testCleansingFilter() throws Exception {

		TaxiRide atPennStation = testRide(-73.9947F, 40.750626F, -73.9947F, 40.750626F);
		TaxiRide toThePole = testRide(-73.9947F, 40.750626F, 0, 90);
		TaxiRide fromThePole = testRide(0, 90, -73.9947F, 40.750626F);
		TaxiRide atNorthPole = testRide(0, 90, 0, 90);

		TestSource source = new TestSource(atPennStation, toThePole, fromThePole, atNorthPole);

		assertEquals(Lists.newArrayList(atPennStation), javaResults(source).values);
		assertEquals(Lists.newArrayList(atPennStation), scalaResults(source).values);
	}

	private TaxiRide testRide(float startLon, float startLat, float endLon, float endLat) {
		return new TaxiRide(1L, true, new DateTime(0), new DateTime(0),
				startLon, startLat, endLon, endLat, (short)1, 0, 0);
	}

}