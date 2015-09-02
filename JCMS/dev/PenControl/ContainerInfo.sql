


-- Add Container Data 


INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 1  , 11 , 'MyContainer11'  , 'MyContainer11 is the pink one.'  );
INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 2  , 12 , 'MyContainer12'  , 'MyContainer12 is the blue one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 3  , 13 , 'MyContainer13'  , 'MyContainer13 is the green one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 4  , 14 , 'MyContainer14'  , 'MyContainer14 is the black one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 5  , 15 , 'MyContainer15'  , 'MyContainer15 is the purple one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 6  , 16 , 'MyContainer16'  , 'MyContainer16 is the red one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 7  , 17 , 'MyContainer17'  , 'MyContainer17 is the yellow one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 8  , 18 , 'MyContainer18'  , 'MyContainer18 is the orange one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 9  , 19 , 'MyContainer19'  , 'MyContainer19 is the cyan one.'  );
-- INSERT INTO Container ( _container_key  , containerID , containerName  , comment ) VALUES ( 10  , 20 , 'MyContainer20'  , 'MyContainer20 is the violet one.'  );

-- Add Rooms

INSERT INTO Room ( _room_key  , roomName ) VALUES ( 1 , 'MyRoom01');
INSERT INTO Room ( _room_key  , roomName ) VALUES ( 2 , 'MyRoom02');
INSERT INTO Room ( _room_key  , roomName ) VALUES ( 3 , 'MyRoom03');
INSERT INTO Room ( _room_key  , roomName ) VALUES ( 4 , 'MyRoom04');

-- Container History

-- Container 1
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 1 , 1, 1 ,'5/1/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 2 , 2, 1 ,'5/1/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 3 , 1, 1 ,'5/1/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 4 , 1, 1 ,'5/1/2009', 3);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 5 , 1, 1 ,'5/1/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 6 , 1, 1 ,'5/1/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 7 , 1, 1 ,'5/1/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 8 , 2, 1 ,'5/1/2009', 3);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 9 , 1, 1 ,'5/1/2009', 3);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 10 , 1, 1 ,'5/1/2009', 2);


-- Container 2
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 11 , 1, 2 ,'5/21/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 12 , 2, 2 ,'5/21/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 13 , 1, 2 ,'5/21/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 14 , 1, 2 ,'5/21/2009', 3);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 15 , 1, 2 ,'5/21/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 16 , 1, 2 ,'5/21/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 17 , 1, 2 ,'5/21/2009', 2);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 18 , 2, 2 ,'5/21/2009', 3);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 19 , 2, 2 ,'5/21/2009', 1);
INSERT INTO ContainerHistory ( _containerHistory_key  , _room_key, _container_key, actionDate, _containerStatus_key ) VALUES ( 20 , 2, 2 ,'5/21/2009', 3);

-- HealthLevel History
-- Room 1 
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 1 , 1, 2 ,'5/4/2009');
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 2 , 1, 1 ,'5/5/2009');
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 3 , 1, 3 ,'5/6/2009');
-- Room 2
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 4 , 2, 1 ,'5/4/2009');
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 5 , 2, 3 ,'5/5/2009');
INSERT INTO HealthLevelHistory ( _healthLevelHistory_key  , _room_key, _healthLevel_key, startDate ) VALUES ( 6 , 2, 2 ,'5/6/2009');

-- Update descriptions in cv_HealthLevel
UPDATE cv_HealthLevel set description = 'Green' Where [_healthLevel_key] = 1 ;
UPDATE cv_HealthLevel set description = 'Yellow' Where [_healthLevel_key] = 2 ;
UPDATE cv_HealthLevel set description = 'Orange' Where [_healthLevel_key] = 3 ;
UPDATE cv_HealthLevel set description = 'Red' Where [_healthLevel_key] = 4 ;

