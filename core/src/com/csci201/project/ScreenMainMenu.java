package com.csci201.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScreenMainMenu implements Screen{
	//class variables
	Game game;
	Stage stage;
	Skin skin;
	
	private TextButton buttonPlay;
	private TextField fieldUserName;
	private TextField fieldHostName;
	
	private Label gameNameLabel;
	private Label usernameLabel;
	private Label hostnameLabel;
	private Label statusLabel;
	
	private Table table;
	
	private int xCen;
	private int yCen;
	
	//constructor
	public ScreenMainMenu(Game g){
		game = g;
		
		initUIElem();
		
		xCen = Gdx.graphics.getWidth()/2;
		yCen = Gdx.graphics.getHeight()/2;
		
		Gdx.input.setInputProcessor(stage);
		
		addUIElem();
		addListeners();
	}
	
	public void initUIElem(){
		stage = new Stage();
		table = new Table();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		gameNameLabel = new Label("FrostByte", skin);
		usernameLabel = new Label("Username:", skin);
		hostnameLabel = new Label("Host:", skin);
		statusLabel = new Label("", skin);
		
		buttonPlay = new TextButton("Play", skin);
		
		fieldHostName = new TextField("", skin);
		fieldUserName = new TextField("", skin);
	}
	
	public void addUIElem(){
		//table.setDebug(true);
		
		table.setPosition(xCen, yCen);
		table.add(gameNameLabel).colspan(2);
		table.row();
		table.add(usernameLabel).pad(10);
		table.add(fieldUserName);
		table.row();
		table.add(hostnameLabel).right().pad(10);
		table.add(fieldHostName);
		table.row();
		table.add(buttonPlay).colspan(2).width(200);
		table.row();
		table.add(statusLabel).colspan(2);
		
		stage.addActor(table);
	}
	
	public void addListeners(){
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button){
				if(fieldUserName.getText().equals("") || fieldHostName.getText().equals("")){
					statusLabel.setText("Please enter a valid username and host IP address");
				}else{
					ChatWindow cw = new ChatWindow(fieldUserName.getText(), fieldHostName.getText());
					statusLabel.setText("Connecting to server... Waiting for other player");				
					game.setScreen(new GameplayScreen(game, fieldUserName.getText()));
				}
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void dispose() {
		Gdx.app.debug("FrostByte", "dispose main menu");
	}

	@Override
	public void resize(int width, int height) {}
	@Override
	public void show() {}
	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
