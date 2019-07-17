@echo off

IF "%1" == "deploy" (
	echo Deploy from %cd%\src\webapp to /home/lvuser/webapp
	scp -r %cd%\src\webapp lvuser@10.51.4.2:
	scp -r %cd%\src\webapp lvuser@172.22.11.2:
) ELSE (
	echo Use "webapp deploy" to deploy the webapp.
)